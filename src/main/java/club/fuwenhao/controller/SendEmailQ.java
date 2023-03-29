package club.fuwenhao.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailQ {
    public static void main(String[] args) {
        String to = "recipient@example.com"; // 收件人邮箱
        String from = "sender@example.com"; // 发件人邮箱
        String host = "smtp.example.com"; // SMTP主机名

        // 设置系统属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        // 获取默认会话对象
        Session session = Session.getDefaultInstance(properties);

        try {
            // 创建一封邮件
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(from));

            // 设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // 设置邮件主题
            message.setSubject("Test Email");

            // 设置邮件正文
            message.setText("This is a test email sent from Java program.");

            // 发送邮件
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
             ex.printStackTrace();
        }
    }
}