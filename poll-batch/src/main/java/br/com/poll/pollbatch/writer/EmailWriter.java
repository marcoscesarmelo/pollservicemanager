package br.com.poll.pollbatch.writer;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.poll.pollbatch.config.WriterConfig;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailWriter {

	private String message;
	
	@Autowired
	private WriterConfig writerConfig;

	public String sendMailResults(String bodyMessage, String to) {
		try {

			this.setMessage(bodyMessage);

			Properties props = new Properties();
			props.put("mail.smtp.auth", writerConfig.getSmtpAuth());
			props.put("mail.smtp.starttls.enable", writerConfig.getSmtpStartTlsEnable());
			props.put("mail.smtp.host", writerConfig.getSmtpHost());
			props.put("mail.smtp.port", writerConfig.getSmtpPort());
			props.put("mail.smtp.ssl.trust", writerConfig.getSmtpSslTrust());
			
			Session session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(writerConfig.getUsername(), writerConfig.getPassword());
						}
					  });

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@poll-service.com.br"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Your poll final result!");
			message.setText(this.getMessage());

			Transport.send(message);

			return "OK, Mail successfuly!";
		} catch (Exception e) {
			return "NOK, Something wrong occurred! " + e.getMessage();
		}
	}
	


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
