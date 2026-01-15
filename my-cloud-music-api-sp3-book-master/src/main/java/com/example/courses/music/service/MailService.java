package com.example.courses.music.service;

import com.example.courses.music.model.User;
import com.example.courses.music.util.IDUtil;
import com.example.courses.music.util.SHAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务
 */
@Service
public class MailService {
    private static Logger log = LoggerFactory.getLogger(MailService.class);

    /**
     * 当前应用地址
     */
    @Value("${info.host}")
    private String host;


    /**
     * 邮件发送者
     */
    private String from = "爱学啊 <ixueadev@163.com>";

    private JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * 发送纯文本测试邮件
     *
     * @param to
     */
    public void sendSimpleMail(String to) {
        //邮件主题
        String subject = "这是标题";

        //邮件内容
        String content = "这是内容";

        //创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);

        //设置邮件信息
        message.setSubject(subject);
        message.setText(content);

        try {
            //发送邮件
            mailSender.send(message);

            log.info("sendSimpleMail success {}", to);
        } catch (MailException e) {
            log.error("sendSimpleMail error {}", e);
        }

    }


    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendHTMLMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            //创建helper
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //发送者
            helper.setFrom(from);

            //发送给谁
            helper.setTo(to);

            //主题
            helper.setSubject(subject);

            //内容
            helper.setText(content, true);

            //发送
            mailSender.send(message);

            log.info("sendHTMLMail success {}", to);
        } catch (MessagingException e) {
            log.error("sendHTMLMail failed {}", e);
        }
    }

    /**
     * 发送邮件验证码
     *
     * @param to
     * @param code
     */
    public void sendEmailCode(String to, String code) {
        //创建邮件正⽂文
        //Context是导这个包org.thymeleaf.context.Context
        Context context = new Context();

        //设置模板需要替换的参数
        context.setVariable("code", code);

        //使用模板引擎处理模板
        String content = templateEngine.process("email_code", context);

        //发送邮件
        sendHTMLMail(to, "【爱学啊】验证码通知", content);
    }

    /**
     * 发送邮箱激活邮件
     *
     * @param data
     * @return
     */
    public String requestEmailVerification(User data) {
        //生成随机字符串
        String confirmation = SHAUtil.sha256(IDUtil.getUUID());

        //邮件可能属于敏感信息
        //真实项目中根据实际情况打印
        log.info("requestEmailVerification {} {} {}", data.getId(), data.getEmail(), confirmation);

        //创建邮件正⽂文
        //Context是导这个包org.thymeleaf.context.Context
        Context context = new Context();

        //设置模板需要替换的参数
        context.setVariable("nickname", data.getNickname());
        context.setVariable("email", data.getEmail());
        context.setVariable("confirmUrl", String.format("%s/mails/%s/confirm_verification", host, confirmation));

        //使用模板引擎处理模板
        String content = templateEngine.process("email_confirm", context);

        //发送邮件
        sendHTMLMail(data.getEmail(), "【爱学啊】邮箱确认通知", content);

        return SHAUtil.sha256(confirmation);
    }
}