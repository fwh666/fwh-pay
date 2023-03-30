package club.fuwenhao.utils;

import club.fuwenhao.bean.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author fuwenhao
 * @createDate 2023/3/30 11:03
 * @descripton
 */
@Slf4j
public class MailUtil {


    /**
     * 发送邮件
     *
     * @author fuwenhao
     * @createDate 2023/3/30 11:04
     * @descripton
     */
    public static boolean sendEmail(String recipientAccount, AccountDto accountDto) {
        // 发件人邮箱账号
        String senderAccount = "fuwenhao@88.com";
        // 发件人邮箱密码（注意不是登录密码，而是授权码）
        String senderPassword = "TtsepcsBbiTIK6ww";
        // 收件人邮箱账号
//        String recipientAccount = "@163.com";
        // 邮件主题
        String subject = "VPN特惠账号";
        // 邮件内容
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>VPN特惠账号</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>VPN特惠账号详情</h1>\n" +
                "<p>您好，</p>\n" +
                "<p>这是您购买的VPN账号信息。</p>\n" +
                "<ul>\n" +
                "    <li>账号: " + accountDto.getAccount() + "</li>\n" +
                "    <li>密码: " + accountDto.getPassword() + "</li>\n" +
                "</ul>\n" +
                "<p>谢谢！</p>\n" +
                "</body>\n" +
                "</html>";

        // 邮件发送服务器的地址和端口号
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.88.com"); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.port", "465"); // QQ邮箱SMTP服务器端口号
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
//        // 启用SSL/TLS加密
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        // 创建Session实例
        Session session = Session.getInstance(props);
        try {
            // 创建邮件消息对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人信息
            message.setFrom(new InternetAddress(senderAccount));
            // 设置收件人信息
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAccount));
            // 设置邮件主题
            message.setSubject(subject);
            // 设置邮件正文
            message.setContent(content, "text/html; charset=utf-8");
            // 创建Transport实例，并连接到邮件服务器
            Transport transport = session.getTransport();
            transport.connect(senderAccount, senderPassword);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();
            log.info("邮件发送成功.收件人:{}", recipientAccount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败.收件人:{}", recipientAccount);
        }
        return false;
    }
}
