package club.fuwenhao.controller;

import club.fuwenhao.bean.dto.AccountDto;
import club.fuwenhao.bean.entity.FwhAccount;
import club.fuwenhao.bean.entity.FwhOrderRecord;
import club.fuwenhao.common.ResEntity;
import club.fuwenhao.config.AlipayConfig;
import club.fuwenhao.enums.PayStatusEnum;
import club.fuwenhao.service.AlipayService;
import club.fuwenhao.service.FwhAccountService;
import club.fuwenhao.service.FwhOrderRecordService;
import club.fuwenhao.utils.EmailUtil;
import club.fuwenhao.utils.MailUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private FwhOrderRecordService orderRecordService;
    @Resource
    private FwhAccountService accountService;


    @GetMapping("/getWebPay")
    public void getWebPay(@RequestParam("email") String email, @RequestParam("orderNo") String orderNo, HttpServletResponse response) {
        if (EmailUtil.isValidEmail(email)) {
            alipayService.tradePagePay(email, orderNo, response);
        }
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
    public String aliNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                //更新本地订单状态
                String outTradeNo = requestParams.get("out_trade_no")[0];
                orderRecordService.update(new UpdateWrapper<FwhOrderRecord>().lambda().eq(FwhOrderRecord::getOutTradeNo, outTradeNo).set(FwhOrderRecord::getStatus, PayStatusEnum.success.getCode()));
                //发送邮件
                FwhOrderRecord orderRecord = orderRecordService.getOne(new LambdaQueryWrapper<FwhOrderRecord>().select(FwhOrderRecord::getRecipientAccount).eq(FwhOrderRecord::getOutTradeNo, outTradeNo));
                String email = orderRecord.getRecipientAccount();
                //获取要发送的账号
                List<FwhAccount> accounts = accountService.list(new LambdaQueryWrapper<FwhAccount>().eq(FwhAccount::getStatus, 0));
                if (CollectionUtils.isEmpty(accounts)) {
                    return "支付成功,账号售罄,请联系管理员767137738退款";
                }
                FwhAccount account = accounts.get(0);
                boolean b = MailUtil.sendEmail(email, new AccountDto().setAccount(account.getAccount()).setPassword(account.getPassword()));
                if (b) {
                    orderRecordService.update(new UpdateWrapper<FwhOrderRecord>().lambda().eq(FwhOrderRecord::getOutTradeNo, outTradeNo).set(FwhOrderRecord::getStatus, PayStatusEnum.email_succeeded.getCode()).set(FwhOrderRecord::getAccount, account.getAccount()));
                    return "支付成功,请查看邮箱📮";
                } else {
                    orderRecordService.update(new UpdateWrapper<FwhOrderRecord>().lambda().eq(FwhOrderRecord::getOutTradeNo, outTradeNo).set(FwhOrderRecord::getStatus, PayStatusEnum.email_failed.getCode()));
                    return "支付成功,邮件未成功发送,联系售后QQ:767137738";
                }
            }
        } catch (Throwable e) {
            log.error("exception: ", e);
        }
        return "支付失败";
    }

}
