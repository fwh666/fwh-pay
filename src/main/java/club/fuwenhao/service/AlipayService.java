package club.fuwenhao.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author tony
 * @date 2020/8/19 13:42
 */
public interface AlipayService {

    /**
     * 网页支付-会跳转到阿里的支付页面
     *
     * @param response
     * @author fuwenhao
     * @createDate 2023/3/28 15:52
     * @descripton 网页支付
     */
    void tradePagePay(String email, HttpServletResponse response);

    /**
     * 获取二维码支付链接-下单返回链接
     *
     * @author fuwenhao
     * @createDate 2023/3/28 16:17
     * @descripton
     */
    String tradePrecreate(String totalAmount, String subject);

    /**
     * 订单号查询
     *
     * @author fuwenhao
     * @createDate 2023/3/28 16:25
     * @descripton
     */
    String tradeQuery(String out_trade_no);


//    /**
//     * 支付宝支付接口
//     *
//     * @return
//     * @throws Exception
//     */
//    public String newAliOrder() throws Exception;
//

    /**
     * 扫码付款后支付宝回调接口
     *
     * @param param
     * @throws Exception
     */
    void aliNotify(Map<String, String> param) throws Exception;

    /**
     * 查询支付宝订单
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    AlipayTradeQueryResponse queryOrder(String orderId) throws Exception;


}
