package club.fuwenhao.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import club.fuwenhao.config.ApiException;
import club.fuwenhao.config.RetEnum;
import club.fuwenhao.consts.AlipayConsts;
import club.fuwenhao.service.AlipayService;
import club.fuwenhao.vo.AlipayBean;
import club.fuwenhao.vo.AlipayJsonRootBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tony
 * @date 2020/8/19 13:42
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    /**支付宝请求地址*/
//    private static String aliUrl = "https://openapi.alipay.com/gateway.do";//正式环境
    private static String aliUrl = "https://openapi.alipaydev.com/gateway.do";//沙箱环境
    /**支付宝应用ID*/
    private static String aliAppId = "2021000122669250";
    /**本地通过"支付宝开放平台开发助手"生成的私钥*/
    private static String aliAppPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRGVXz/jsJP7BRwkc34LZcX034EgMGj1EkiYQa56HXQvZSy77kxUsrvM2F2JmAMulETE62an/7rK8DnM3V6fRIBOJroxk/0tijQe7KQSccHwCjlKkaADvHpcn7TUVnEEoz5mNA5h3EA5WtFpfktNoxiBLTbQZPIIG4wbIkhbHVJKuf8vLNpemmBXMLcOZVRoBFY9UQJ86Quvq/vSI9d3fby7Iw2Ty9O2ulpbPSWtkMmR7VOtu0X7M90qCkPYVGPTPksAU41B7rSVJHL1Zbz3volM+h5hpUCAT5bm+P2L++qNTYb16GPhtZeoOvh0m5Q0q+J34lLcAwbfXSHiiapTxHAgMBAAECggEBAIO0Spe5WR7xB3t+7CQlPYIlI+Gbf9GRfya6CAZf4EBDUNEgjXqcNrpRmv/19ocuLxxGY2Ai1V69hPKzfwa/YHOKs3beSYnsOaYer8A4WWamIW9Z/hBSy/BRZUBNCEUfvSrU4ZzEA5qrYk4FZwQ6wJ8bE3ODz6k9KWJptuh9zkhXqKpsUNb0Ox7b4YEjXcNumj/ROXgX/IvhL5G+ZdyIPxuSMktwnqVpK+JvN1hsiGWUtv5ZCL2EfBYasXK6g5UoGh0+8augXGBZ1WUD5gVo+dKqUCRRqcu1YidS5i9dXyG4ArI1qiWJnBvfpJ88InNpPAUgCZthmhBqjjXaCakxGvkCgYEA5SORVO++nJRp7GmPp1oEr8x8zBSoyri/YgRiPhL14jzY9mKrZT2ycnJkGzK4Mu4vvPeOEQMlj3NT6WrHOXJGAyI7gZTz/g03K/rpF9HkuDaOJ0uKgr2AAjNVLTvAXQ5LPYtirp7oXJPkiwQVO6ZbLySyo9NyGgjEUyPWv7NZ4TUCgYEAohu9Epwldb2LMDtOs1+uW4y7FSWctvnQPvQOvkQIqteQLj2DqCAkGVpjHeYl6Nft2Dt7UIKmV+ur202Img7HLg0cgAxw3MVO0QwKJ6fphQ0tUhj5W0X9wiiay57lR56pGQciTC29mQzGVfk8ZpkXBs7QmFu6osSdpctzd2afMwsCgYEAgcNEuG8U6SN7YPDe840c/lm1ivVgKX26lE2bPUALk1WWIOH74lewSPPTETwF6IHO8xrWj3fSu4w6RwO7UyMN1xR50oaCLqtZRUTQ4DZzaocqtcIn7KZYaeacJAOkio5fubjH59ACEvuF/9wOEjvBg88qg3BpO6kfVV4EbYeqLMkCgYARM3RKhzsKVURxp7lgGyT7HeG0CruoRrWsGGWAFuP8jMcFwQ05R7/M2ORvhb48CL16FkWtc0+HQMCJkp6OCkdkQYvmomtPYbhNQkPJlW2X6qAGeBPtdW3JrllOhdu6T8GNoE4pWyklFk/tS3b3RANHe6ZypDkXkFlSAFaOMtttpQKBgFEz23hFmrESpqkr6+0kLweZMSE8xOXZKvwCGq6nsIRvJMn+jgWFin/Imt7kbLJXhhLb1dxtW1yGhSwUC7bD7UzHIuHkgpepMgT1tl52ZP+yquHUKabn+N3uHgzkldSHXU9JYzzLfGyrrhEEOoD4KQ3eDslZWUwL+nzuRRjt1JA3";
    /**支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）*/
    private static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlIu/GE3CbV8DX2TArHvbH9Zna8OfwllEJ2MGoJEG+hrPTAXhf04aALixx9wdo/Q0GmhRYwiQ2mDQZmzGWo5wT1sdwdnAvM8AiSkmdyuy5h3JP51SJ+HZTyFEZgr7jcPe7J1L1zHlVT36Pf7GGEHizGBgp6HHHV+dxF+3NUi6mDxHxyX69lk/jvX2LgpGG+yz6R43DSSR5Y/QqC1r+hBI6TFNYxUoXrp2kVms3LR4kHLzBH1Y/8e/HttAm6CMfeZGOyLjEIyeQcJCLLKid9Y66n9/VuewwoZL1bDeYAWm3pUp+wrjP4aOWD5BOCcpIACGtAaky9cMt8xXdjQW1YdTwQIDAQAB";
    /**支付宝回调的接口地址*/
    private static String aliNotifyUrl = "http://localhost:8090/alinotify";

    @Override
    public String newAliOrder() throws Exception {
        log.info("开始调用支付宝生成支付二维码...");
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        //设置请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//        model.setOutTradeNo("20200826888888888888888");
//        model.setTotalAmount("1");
//        model.setSubject("充值");
//        //如果没有店铺号可不设置
//        model.setStoreId("9527");
//        model.setQrCodeTimeoutExpress("10m");

        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");

        request.setBizModel(model);
        //支付宝异步通知地址
        request.setNotifyUrl(aliNotifyUrl);
        log.info("创建支付宝订单，请求参数：{} ", JSONObject.toJSONString(request));
        //调用接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        log.info("创建支付宝订单，返回值：{} ", JSONObject.toJSONString(response));
        if (!response.isSuccess()) {
            throw new ApiException(RetEnum.MachineOrderAlipayException);
        }
        AlipayJsonRootBean alipayJsonRootBean = JSONObject.parseObject(response.getBody(), AlipayJsonRootBean.class);
        if(!AlipayConsts.SuccessCode.equals(alipayJsonRootBean.getAlipay_trade_precreate_response().getCode())){
            throw new ApiException(RetEnum.MachineOrderAlipayException);
        }
        log.info("交易订单号outTradeNo：{} ", response.getOutTradeNo());
        log.info("支付二维码qrCode：{} ", response.getQrCode());
        return response.getQrCode();
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
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderId);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("查询支付宝订单，返回数据：{}", response);
        return response;
    }


}
