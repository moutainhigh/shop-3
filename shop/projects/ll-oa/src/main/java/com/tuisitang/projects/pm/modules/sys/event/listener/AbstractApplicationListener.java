package com.tuisitang.projects.pm.modules.sys.event.listener;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**    
 * @{#} AbstractApplicationListener.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public abstract class AbstractApplicationListener {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 发送Email
	 * 
	 * @param subject
	 * @param to
	 * @param content
	 */
	protected void sendEmail(String subject, String to, String content) {
		logger.info("subject {}, to {}, content {}", subject, to, content);
		final String userName = "kf@baoxiliao.com";
		final String password = "llxf19871016";
//		final String password = "leilifengxing";
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.qq.com");
			properties.put("mail.smtp.port", "25");
			properties.put("mail.smtp.auth", "true");
			Session sendMailSession = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			MimeMessage mailMessage = new MimeMessage(sendMailSession);
			mailMessage.setFrom(new InternetAddress(userName));
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mailMessage.setSubject(subject, "UTF-8");
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			html.setContent(content.trim(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
