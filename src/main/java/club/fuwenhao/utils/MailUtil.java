package club.fuwenhao.utils;

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
public class MailUtil {

    /**
     * 发送邮件
     *
     * @author fuwenhao
     * @createDate 2023/3/30 11:04
     * @descripton
     */
    public static boolean sendEmail(String recipientAccount) {
        // 发件人邮箱账号
        String senderAccount = "767137738@qq.com";
        // 发件人邮箱密码（注意不是登录密码，而是授权码）
        String senderPassword = "your_password";
        // 收件人邮箱账号
//        String recipientAccount = "fuwenhao594@163.com";
        // 邮件主题
        String subject = "邮件主题";
        // 邮件内容
        String content = "邮件内容";

        // 邮件发送服务器的地址和端口号
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.qq.com"); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.port", "587"); // QQ邮箱SMTP服务器端口号
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
//        // 启用SSL/TLS加密
//        props.setProperty("mail.smtp.starttls.enable", "true");

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
            message.setText(content);

            // 创建Transport实例，并连接到邮件服务器
            Transport transport = session.getTransport();
            transport.connect(senderAccount, senderPassword);

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());

            // 关闭连接
            transport.close();

            System.out.println("邮件发送成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送失败");
        }
        return false;
    }
}
