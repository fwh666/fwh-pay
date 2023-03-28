package club.fuwenhao.controller;

import club.fuwenhao.common.ResEntity;
import club.fuwenhao.config.AlipayConfig;
import club.fuwenhao.service.AlipayService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fuwenhao
 * @createDate 2023/3/28 15:50
 * @descripton 支付相关接口- 参考:https://blog.csdn.net/yyongsheng/article/details/127072001
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class AlipayPayController {
    @Resource
    private AlipayService alipayService;

    @GetMapping("/getWebPay")
    public void getWebPay(HttpServletResponse response) {
        alipayService.tradePagePay(response);
    }

    @GetMapping("/getPayQrCode")
    public ResEntity<String> getPayQrCode() {
        String totalAmount = "19.9";
        String subject = "VPN特惠账号";
        String qrCode = alipayService.tradePrecreate(totalAmount, subject);
        return new ResEntity().setCode("0").setData(qrCode);
    }

    @GetMapping("/alipayQuery")
    public String alipayQuery(@RequestParam("tradeNo") String tradeNo) {
        return alipayService.tradeQuery(tradeNo);
    }

    /**支付宝回调接口*/
    /**
     * 不返回success，支付宝会在25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）才会结束通知发送。
     */
    @RequestMapping(value = "alinotify")
    public String aliNotify(HttpServletRequest request) throws Exception {
        try {
            log.info("进入支付宝回调地址");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            log.info("支付宝验签参数：{}", JSON.toJSONString(requestParams));
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", "RSA2");
            if (flag) {
                alipayService.aliNotify(params);
                log.info("支付宝通知更改状态成功！");
                return "success";
            }
        } catch (Throwable e) {
            log.error("exception: ", e);
        }
        return "failure";
    }

}
