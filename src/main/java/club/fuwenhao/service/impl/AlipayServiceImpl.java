package club.fuwenhao.service.impl;


import club.fuwenhao.bean.entity.FwhOrderRecord;
import club.fuwenhao.config.AlipayConfig;
import club.fuwenhao.service.FwhOrderRecordService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import club.fuwenhao.config.ApiException;
import club.fuwenhao.config.RetEnum;
import club.fuwenhao.consts.AlipayConsts;
import club.fuwenhao.service.AlipayService;
import club.fuwenhao.vo.AlipayBean;
import club.fuwenhao.vo.AlipayJsonRootBean;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import static club.fuwenhao.config.AlipayConfig.*;

/**
 * @author tony
 * @date 2020/8/19 13:42
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

//    /**
//     * //     * 支付宝请求地址
//     * //
//     */
////    private static String aliUrl = "https://openapi.alipay.com/gateway.do";//正式环境
//    private static String aliUrl = "https://openapi.alipaydev.com/gateway.do";//沙箱环境
//    /**
//     * 支付宝应用ID
//     */
//    private static String aliAppId = "2021000122669250";
//    /**
//     * 本地通过"支付宝开放平台开发助手"生成的私钥
//     */
//    private static String aliAppPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRGVXz/jsJP7BRwkc34LZcX034EgMGj1EkiYQa56HXQvZSy77kxUsrvM2F2JmAMulETE62an/7rK8DnM3V6fRIBOJroxk/0tijQe7KQSccHwCjlKkaADvHpcn7TUVnEEoz5mNA5h3EA5WtFpfktNoxiBLTbQZPIIG4wbIkhbHVJKuf8vLNpemmBXMLcOZVRoBFY9UQJ86Quvq/vSI9d3fby7Iw2Ty9O2ulpbPSWtkMmR7VOtu0X7M90qCkPYVGPTPksAU41B7rSVJHL1Zbz3volM+h5hpUCAT5bm+P2L++qNTYb16GPhtZeoOvh0m5Q0q+J34lLcAwbfXSHiiapTxHAgMBAAECggEBAIO0Spe5WR7xB3t+7CQlPYIlI+Gbf9GRfya6CAZf4EBDUNEgjXqcNrpRmv/19ocuLxxGY2Ai1V69hPKzfwa/YHOKs3beSYnsOaYer8A4WWamIW9Z/hBSy/BRZUBNCEUfvSrU4ZzEA5qrYk4FZwQ6wJ8bE3ODz6k9KWJptuh9zkhXqKpsUNb0Ox7b4YEjXcNumj/ROXgX/IvhL5G+ZdyIPxuSMktwnqVpK+JvN1hsiGWUtv5ZCL2EfBYasXK6g5UoGh0+8augXGBZ1WUD5gVo+dKqUCRRqcu1YidS5i9dXyG4ArI1qiWJnBvfpJ88InNpPAUgCZthmhBqjjXaCakxGvkCgYEA5SORVO++nJRp7GmPp1oEr8x8zBSoyri/YgRiPhL14jzY9mKrZT2ycnJkGzK4Mu4vvPeOEQMlj3NT6WrHOXJGAyI7gZTz/g03K/rpF9HkuDaOJ0uKgr2AAjNVLTvAXQ5LPYtirp7oXJPkiwQVO6ZbLySyo9NyGgjEUyPWv7NZ4TUCgYEAohu9Epwldb2LMDtOs1+uW4y7FSWctvnQPvQOvkQIqteQLj2DqCAkGVpjHeYl6Nft2Dt7UIKmV+ur202Img7HLg0cgAxw3MVO0QwKJ6fphQ0tUhj5W0X9wiiay57lR56pGQciTC29mQzGVfk8ZpkXBs7QmFu6osSdpctzd2afMwsCgYEAgcNEuG8U6SN7YPDe840c/lm1ivVgKX26lE2bPUALk1WWIOH74lewSPPTETwF6IHO8xrWj3fSu4w6RwO7UyMN1xR50oaCLqtZRUTQ4DZzaocqtcIn7KZYaeacJAOkio5fubjH59ACEvuF/9wOEjvBg88qg3BpO6kfVV4EbYeqLMkCgYARM3RKhzsKVURxp7lgGyT7HeG0CruoRrWsGGWAFuP8jMcFwQ05R7/M2ORvhb48CL16FkWtc0+HQMCJkp6OCkdkQYvmomtPYbhNQkPJlW2X6qAGeBPtdW3JrllOhdu6T8GNoE4pWyklFk/tS3b3RANHe6ZypDkXkFlSAFaOMtttpQKBgFEz23hFmrESpqkr6+0kLweZMSE8xOXZKvwCGq6nsIRvJMn+jgWFin/Imt7kbLJXhhLb1dxtW1yGhSwUC7bD7UzHIuHkgpepMgT1tl52ZP+yquHUKabn+N3uHgzkldSHXU9JYzzLfGyrrhEEOoD4KQ3eDslZWUwL+nzuRRjt1JA3";
//    /**
//     * 支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）
//     */
//    private static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlIu/GE3CbV8DX2TArHvbH9Zna8OfwllEJ2MGoJEG+hrPTAXhf04aALixx9wdo/Q0GmhRYwiQ2mDQZmzGWo5wT1sdwdnAvM8AiSkmdyuy5h3JP51SJ+HZTyFEZgr7jcPe7J1L1zHlVT36Pf7GGEHizGBgp6HHHV+dxF+3NUi6mDxHxyX69lk/jvX2LgpGG+yz6R43DSSR5Y/QqC1r+hBI6TFNYxUoXrp2kVms3LR4kHLzBH1Y/8e/HttAm6CMfeZGOyLjEIyeQcJCLLKid9Y66n9/VuewwoZL1bDeYAWm3pUp+wrjP4aOWD5BOCcpIACGtAaky9cMt8xXdjQW1YdTwQIDAQAB";
//    /**
//     * 支付宝回调的接口地址
//     */
//    private static String aliNotifyUrl = "http://localhost:8090/alinotify";

    @Autowired
    private FwhOrderRecordService orderRecordService;

    @Override
    public void tradePagePay(String email, String orderNo, HttpServletResponse response) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key,
                AlipayConfig.json_type, AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//        String outTradeNo = String.valueOf(System.currentTimeMillis());
        model.setOutTradeNo(orderNo);
        model.setTotalAmount("19.9");
        model.setSubject("VPN特惠账号");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizModel(model);


        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
//                + "\"total_amount\":\"" + total_amount + "\","
//                + "\"subject\":\"" + subject + "\","
//                + "\"body\":\"" + body + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        try {
            AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);
            log.info("email:{},outTradeNo:{},响应:{}", email, orderNo, JSONObject.toJSONString(alipayTradePagePayResponse));
            //成功入库
            FwhOrderRecord orderRecord = new FwhOrderRecord();
            orderRecord.setOrderNo(Long.valueOf(orderNo)).setOutTradeNo(orderNo).setRecipientAccount(email).setStatus(0).setCreateTime(new Date()).setModifiedTime(new Date());
            orderRecordService.save(orderRecord);


            String result = alipayTradePagePayResponse.getBody();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            log.info(JSONObject.toJSONString(alipayTradePagePayResponse));
            out.write(result);
            out.close();
        } catch (Exception e) {
            log.error("支付页面异常.入参:{}", JSONObject.toJSONString(alipayRequest), e);
        }

    }

    @Override
    public String tradePrecreate(String totalAmount, String subject) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key,
                AlipayConfig.json_type, AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);

        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setTotalAmount(totalAmount);
        model.setSubject(subject);
//        model.setTimeExpire("10m");
        alipayRequest.setBizModel(model);

//        JSONObject bizContent = new JSONObject();
//        bizContent.put("out_trade_no", String.valueOf(System.currentTimeMillis()));
//        bizContent.put("total_amount", "1.8");
//        bizContent.put("subject", "Iphone6 16G");
////        bizContent.put("body", body);
////        bizContent.put("timeout_express", timeout_express);//二维码有效时间 20m(20分钟，可以秒 分钟 天)
//        alipayRequest.setBizContent(bizContent.toString());


        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {
                return response.getQrCode();
            } else {
                return response.getMsg() + "," + response.getSubCode();
            }
        } catch (AlipayApiException e) {
            log.error("获取二维码链接异常", e);
        }
        return "";

    }

    @Override
    public String tradeQuery(String out_trade_no) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.json_type, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        bizContent.put("trade_no", "");
        alipayRequest.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse alipayTradeQueryResponse = null;
        try {
            alipayTradeQueryResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            log.error("查询交易订单异常", e);
            return "";
        }
        if (alipayTradeQueryResponse.isSuccess()) {
            if (alipayTradeQueryResponse.getTradeStatus().equals("WAIT_BUYER_PAY")) {
                return "交易创建，等待买家付款";
            } else if (alipayTradeQueryResponse.getTradeStatus().equals("TRADE_CLOSED")) {
                return "未付款交易超时关闭，或支付完成后全额退款";
            } else if (alipayTradeQueryResponse.getTradeStatus().equals("TRADE_SUCCESS")) {
                return "交易支付成功";
            } else if (alipayTradeQueryResponse.getTradeStatus().equals("TRADE_FINISHED")) {
                return "交易结束，不可退款";
            }
        } else {
            log.info(JSONObject.toJSONString(alipayTradeQueryResponse));
            return alipayTradeQueryResponse.getSubMsg();
        }
        return alipayTradeQueryResponse.getBody();
    }

    @Override
    public void aliNotify(Map<String, String> param) throws Exception {
        log.info("支付宝异步回调接口数据处理");
        //只有支付成功后，支付宝才会回调应用接口，可直接获取支付宝响应的参数
        String order_id = param.get(AlipayConsts.AliOutTradeNo);
        //出于安全考虑，通过支付宝回传的订单号查询支付宝交易信息
        AlipayTradeQueryResponse aliResp = queryOrder(order_id);
        if (!AlipayConsts.SuccessCode.equals(aliResp.getCode())) {
            //返回值非10000
            throw new ApiException(RetEnum.MachineOrderAlipayException, aliResp.getSubMsg());
        }
        if (!AlipayConsts.AliTradeSuccess.equals(aliResp.getTradeStatus()) && !AlipayConsts.AliTradeFinished.equals(aliResp.getTradeStatus())) {
            //支付宝订单状态不是支付成功
            throw new ApiException(RetEnum.MachineOrderAliUnPay);
        }
        //可对支付宝响应参数AlipayTradeQueryResponse进行处理

    }

    @Override
    public AlipayTradeQueryResponse queryOrder(String orderId) throws Exception {
        log.info("查询支付宝订单，订单编号为：{}", orderId);
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", "utf-8", alipay_public_key, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderId);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("查询支付宝订单，返回数据：{}", response);
        return response;
    }


}
