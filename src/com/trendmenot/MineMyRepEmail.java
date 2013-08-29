package com.trendmenot;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.trendmenot.data.Email;


public class MineMyRepEmail {

	/**
	 * @param args
	 */
	public void SendEmail(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("minemyrep510@gmail.com","gentleben");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dummy0412@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("minemyrep510@gmail.com"));
			message.setSubject(email.getSubject());
			message.setText("Name:"+email.getName()+"\n From:"+email.getEmail()+" \n"+email.getMessage());
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
