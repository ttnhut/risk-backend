package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.services.EmailService;
import com.utc2.riskmanagement.utils.EmailConstant;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendActivationEmail(String to, String urlButton, String title, String description, String name) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        message.setTo(to);
        if (title.equals(EmailConstant.ACTIVATION_SUBJECT)) {
            message.setSubject(EmailConstant.ACTIVATION_SUBJECT);
        }
        else {
            message.setSubject(EmailConstant.ASSIGN_RISK_SUBJECT);
        }

        // Create Thymeleaf context
        Context context = new Context();
        context.setVariable(EmailConstant.URL_BUTTON_PARAM, urlButton);
        context.setVariable(EmailConstant.TITLE_PARAM, title);
        context.setVariable(EmailConstant.DESCRIPTION_PARAM, description);
        context.setVariable(EmailConstant.NAME_PARAM, name);

        // Process the Thymeleaf template
        String emailContent = templateEngine.process("email-template", context);

        // Set the email content
        message.setText(emailContent, true);

        // Send the email
        mailSender.send(message.getMimeMessage());
    }

    @Override
    public void sendProgressRiskEmail(String to, String title, String status) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        message.setTo(to);
            message.setSubject(EmailConstant.RISK_STATUS_SUBJECT);

        // Create Thymeleaf context
        Context context = new Context();
        context.setVariable(EmailConstant.TITLE_PARAM, title);
        context.setVariable(EmailConstant.STATUS_PARAM, status);

        // Process the Thymeleaf template
        String emailContent = templateEngine.process("progress-template", context);

        // Set the email content
        message.setText(emailContent, true);

        // Send the email
        mailSender.send(message.getMimeMessage());
    }
}
