package com.elephant.utils;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.elephant.constant.StatusCode;
import com.elephant.model.mail.Mail;
import com.elephant.response.Response;

@Component
public class SmtpMailSender {
	 @Autowired
	    private JavaMailSender javaMailSender;
	   
	   
	    public void send(String to,String subject,String body) throws MessagingException{
	        MimeMessage message=javaMailSender.createMimeMessage();
	        MimeMessageHelper helper;
	        helper=new MimeMessageHelper(message,true);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body);
	        javaMailSender.send(message);
	    }

	    public void sendMail(Mail mail,String body) throws MessagingException {
	    		MimeMessage message = javaMailSender.createMimeMessage();
		    	MimeMessageHelper helper = new MimeMessageHelper(message,
		                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
		                StandardCharsets.UTF_8.name());
		    	helper.addAttachment("logo.png",new ClassPathResource("logo.png"));
		    			//new ClassPathResource(Paths.get(".").toAbsolutePath().toString()+"/logo.png"));
		        helper.setTo(mail.getTo());
		        helper.setText(body, true);
		        helper.setSubject(mail.getSubject());
		        helper.setFrom(mail.getFrom());
		        
		        javaMailSender.send(message);
	    }
}