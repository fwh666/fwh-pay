package club.fuwenhao.service.impl;


import club.fuwenhao.bean.entity.FwhOrderRecord;
import club.fuwenhao.config.AlipayConfig;
import club.fuwenhao.config.ApiException;
import club.fuwenhao.config.RetEnum;
import club.fuwenhao.consts.AlipayConsts;
import club.fuwenhao.service.AlipayService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author tony
 * @date 2020/8/19 13:42
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Value("${Alipay.notify_url}")
    private String notify_url;
    @Value("${Alipay.return_url}")
    private String return_url;
    @Value("${Alipay.gateway_url}")
    private String gatewayUrl;
    @Value("${Alipay.app_id}")
    private String app_id;
    @Value("${Alipay.merchant_private_key}")
    private String merchant_private_key;
    @Value("${Alipay.alipay_public_key}")
    private String alipay_public_key;

    @Autowired
    private FwhOrderRecordService orderRecordService;

    @Override
    public void tradePagePay(String email, String orderNo, HttpServletResponse response) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key,
                AlipayConfig.json_type, AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
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

        try {
            AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);
            log.info("email:{},outTradeNo:{},响应:{}", email, orderNo, JSONObject.toJSONString(alipayTradePagePayResponse));
//            //成功入库
            FwhOrderRecord orderRecord = new FwhOrderRecord();
            orderRecord.setOrderNo(Long.valueOf(orderNo)).setOutTradeNo(orderNo).setRecipientAccount(email).setStatus(0).setCreateTime(new Date()).setModifiedTime(new Date());
            orderRecordService.save(orderRecord);

            String result = alipayTradePagePayResponse.getBody();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
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
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, AlipayConfig.json_type, AlipayConfig.charset, alipay_public_key, AlipayConfig.sign_type);

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
