package com.invessa.messaging.email.service;

import com.invessa.messaging.email.model.Mail;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.invessa.messaging.email.request.EmailRequest;
import com.invessa.messaging.response.MessageResponse;
import com.invessa.messaging.sms.response.ErrorResponse;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromMailAddress;

    @Value("${mail.from.name}")
    private String fromMailName;

    Map<String, List<String>> error = new HashMap<>();
    List<String> errorList = new ArrayList<>();

    public ResponseEntity<?>sendEmail(EmailRequest emailRequest){
        String emailType = emailRequest.getEmail_type().toString();
        if(emailType.equals("REGISTRATION") || emailType.equals("CONFIRMATION")){
            return sendRegistrationConfirmationEmail(emailRequest);
        }else{
            return null;
        }
    }

    public void sendEmail(Mail mail) throws MessagingException, IOException, jakarta.mail.MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));

        Context context = new Context();
        context.setVariables(mail.getProps());

        final String template = mail.getProps().get("type").equals("NEWSLETTER") ? "newsletter-template" : "inlined-css-template";
        String html = templateEngine.process(template, context);

        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    private ResponseEntity<?> sendRegistrationConfirmationEmail(EmailRequest emailRequest){
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
            String emailSubject = "";
            String emailTemplate = "";
            String emailType = emailRequest.getEmail_type().toString();
            if(emailType.equals("REGISTRATION")){
                emailSubject = "Finish your registration";
                emailTemplate = "registration.html";
            }else if(emailType.equals("CONFIRMATION")){
                emailSubject = "Welcome to Invessa";
                emailTemplate = "confirmation.html";
            }

            helper.setTo(emailRequest.getTo_address());
            helper.setSubject(emailSubject);
            helper.setFrom(new InternetAddress(fromMailAddress,fromMailName));

            final Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("email",emailRequest.getTo_address());
            context.setVariable("subject",emailSubject);
            context.setVariable("name", emailRequest.getFirst_name()+" "+emailRequest.getLast_name());
            final String htmlContent = templateEngine.process(emailTemplate, context);
            helper.setText(htmlContent,true);
            emailSender.send(message);
        }catch (jakarta.mail.MessagingException | UnsupportedEncodingException me){
            errorList.add(me.getMessage());
            error.put("error: ",errorList);
            return new ResponseEntity<>(new ErrorResponse("31","failed","Failure sending email",error), HttpStatus.BAD_REQUEST);
        }
        Map<String,String> info = new HashMap<>();
        return new ResponseEntity<>(new MessageResponse("00","success","Email Sent", info),HttpStatus.OK);
    }

    public boolean sendCustomerRegistrationConfirmationEmail(EmailRequest emailRequest){
        log.info("In EmailSenderService || sendCustomerRegistrationConfirmationEmail");
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            String emailSubject = "";
            String emailTemplate = "";
            String emailType = emailRequest.getEmail_type().toString();
            if(emailType.equals("REGISTRATION")){
                emailSubject = "Finish your registration";
                emailTemplate = "registration.html";
            }else if(emailType.equals("CONFIRMATION")){
                emailSubject = "Welcome to Invessa";
                emailTemplate = "confirmation.html";
            }
            log.info("Building email parameters and body");
            helper.setTo(emailRequest.getTo_address());
            helper.setSubject(emailSubject);
            helper.setFrom(new InternetAddress(fromMailAddress,fromMailName));

            final Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("email",emailRequest.getTo_address());
            context.setVariable("subject",emailSubject);
            context.setVariable("name", emailRequest.getFirst_name()+" "+emailRequest.getLast_name());
            final String htmlContent = templateEngine.process(emailTemplate, context);
            helper.setText(htmlContent,true);
            log.info("email parameters and body  built");
            log.info("Sending Email");
            emailSender.send(message);
            log.info(">> Email Sent");
        }catch (jakarta.mail.MessagingException | UnsupportedEncodingException me){
            errorList.add(me.getMessage());
            error.put("error: ",errorList);
            return false;
        }
        return true;
    }

}
