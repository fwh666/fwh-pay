package club.fuwenhao.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//    public static String app_id = "2021000122669250";
    //测试 "2021000121663822"

    // 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRGVXz/jsJP7BRwkc34LZcX034EgMGj1EkiYQa56HXQvZSy77kxUsrvM2F2JmAMulETE62an/7rK8DnM3V6fRIBOJroxk/0tijQe7KQSccHwCjlKkaADvHpcn7TUVnEEoz5mNA5h3EA5WtFpfktNoxiBLTbQZPIIG4wbIkhbHVJKuf8vLNpemmBXMLcOZVRoBFY9UQJ86Quvq/vSI9d3fby7Iw2Ty9O2ulpbPSWtkMmR7VOtu0X7M90qCkPYVGPTPksAU41B7rSVJHL1Zbz3volM+h5hpUCAT5bm+P2L++qNTYb16GPhtZeoOvh0m5Q0q+J34lLcAwbfXSHiiapTxHAgMBAAECggEBAIO0Spe5WR7xB3t+7CQlPYIlI+Gbf9GRfya6CAZf4EBDUNEgjXqcNrpRmv/19ocuLxxGY2Ai1V69hPKzfwa/YHOKs3beSYnsOaYer8A4WWamIW9Z/hBSy/BRZUBNCEUfvSrU4ZzEA5qrYk4FZwQ6wJ8bE3ODz6k9KWJptuh9zkhXqKpsUNb0Ox7b4YEjXcNumj/ROXgX/IvhL5G+ZdyIPxuSMktwnqVpK+JvN1hsiGWUtv5ZCL2EfBYasXK6g5UoGh0+8augXGBZ1WUD5gVo+dKqUCRRqcu1YidS5i9dXyG4ArI1qiWJnBvfpJ88InNpPAUgCZthmhBqjjXaCakxGvkCgYEA5SORVO++nJRp7GmPp1oEr8x8zBSoyri/YgRiPhL14jzY9mKrZT2ycnJkGzK4Mu4vvPeOEQMlj3NT6WrHOXJGAyI7gZTz/g03K/rpF9HkuDaOJ0uKgr2AAjNVLTvAXQ5LPYtirp7oXJPkiwQVO6ZbLySyo9NyGgjEUyPWv7NZ4TUCgYEAohu9Epwldb2LMDtOs1+uW4y7FSWctvnQPvQOvkQIqteQLj2DqCAkGVpjHeYl6Nft2Dt7UIKmV+ur202Img7HLg0cgAxw3MVO0QwKJ6fphQ0tUhj5W0X9wiiay57lR56pGQciTC29mQzGVfk8ZpkXBs7QmFu6osSdpctzd2afMwsCgYEAgcNEuG8U6SN7YPDe840c/lm1ivVgKX26lE2bPUALk1WWIOH74lewSPPTETwF6IHO8xrWj3fSu4w6RwO7UyMN1xR50oaCLqtZRUTQ4DZzaocqtcIn7KZYaeacJAOkio5fubjH59ACEvuF/9wOEjvBg88qg3BpO6kfVV4EbYeqLMkCgYARM3RKhzsKVURxp7lgGyT7HeG0CruoRrWsGGWAFuP8jMcFwQ05R7/M2ORvhb48CL16FkWtc0+HQMCJkp6OCkdkQYvmomtPYbhNQkPJlW2X6qAGeBPtdW3JrllOhdu6T8GNoE4pWyklFk/tS3b3RANHe6ZypDkXkFlSAFaOMtttpQKBgFEz23hFmrESpqkr6+0kLweZMSE8xOXZKvwCGq6nsIRvJMn+jgWFin/Imt7kbLJXhhLb1dxtW1yGhSwUC7bD7UzHIuHkgpepMgT1tl52ZP+yquHUKabn+N3uHgzkldSHXU9JYzzLfGyrrhEEOoD4KQ3eDslZWUwL+nzuRRjt1JA3";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlIu/GE3CbV8DX2TArHvbH9Zna8OfwllEJ2MGoJEG+hrPTAXhf04aALixx9wdo/Q0GmhRYwiQ2mDQZmzGWo5wT1sdwdnAvM8AiSkmdyuy5h3JP51SJ+HZTyFEZgr7jcPe7J1L1zHlVT36Pf7GGEHizGBgp6HHHV+dxF+3NUi6mDxHxyX69lk/jvX2LgpGG+yz6R43DSSR5Y/QqC1r+hBI6TFNYxUoXrp2kVms3LR4kHLzBH1Y/8e/HttAm6CMfeZGOyLjEIyeQcJCLLKid9Y66n9/VuewwoZL1bDeYAWm3pUp+wrjP4aOWD5BOCcpIACGtAaky9cMt8xXdjQW1YdTwQIDAQAB";//不是应用公钥

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "服务器回调地址";//
//    public static String notify_url = "http://localhost:8090/pay/alinotify";//

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "http://localhost:8090/pay/alinotify/";

    // 签名方式
    public static String sign_type = "RSA2";

    public static String json_type = "json";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
//    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    //正式环境 "https://openapi.alipay.com/gateway.do";
    //沙箱环境 "https://openapi.alipaydev.com/gateway.do"


}
 