package club.fuwenhao.controller;

import club.fuwenhao.service.AlipayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fuwenhao
 * @createDate 2023/3/28 15:50
 * @descripton 支付相关接口- 参考:https://blog.csdn.net/yyongsheng/article/details/127072001
 */
@RestController
public class AlipayPayController {
    @Resource
    private AlipayService alipayService;

    @GetMapping("/getWebPay")
    public void getWebPay(HttpServletResponse response) {
        alipayService.tradePagePay(response);
    }

    @GetMapping("/getPayQrCode")
    public String getPayQrCode() {
        return alipayService.tradePrecreate();
    }

    @GetMapping("/alipayQuery")
    public String alipayQuery(@RequestParam("tradeNo") String tradeNo) {
        return alipayService.tradeQuery(tradeNo);
    }


}
