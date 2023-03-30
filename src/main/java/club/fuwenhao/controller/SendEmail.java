package club.fuwenhao.controller;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
    public static void main(String[] args) throws EmailException {
        String from = "767137738@qq.com"; // 发件人邮箱
        String to = "fuwenhao594@163.com"; // 收件人邮箱
        String password = "XXXXXX"; // 邮箱授权密码

        // 创建一个 HtmlEmail 实例
        HtmlEmail email = new HtmlEmail();

        // 设置邮件服务器参数
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setSSL(true);
        email.setAuthentication(from, password);

        // 设置发件人、收件人、主题和内容
        email.setFrom(from);
        email.addTo(to);
        email.setSubject("Test Email");
        email.setHtmlMsg("<html><body><h1>Test Email</h1><p>This is a test email sent from Java program.</p></body></html>");

        // 发送邮件
        email.send();

        System.out.println("Email sent successfully.");
    }
}