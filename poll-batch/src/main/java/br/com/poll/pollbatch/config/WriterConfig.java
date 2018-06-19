package br.com.poll.pollbatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:general.properties")
public class WriterConfig {
	
	private String consumerKey = "d0wUpL8WtpKrTiklWDBnJjYMJ";
	private String consumerSecretKey = "J3RpnhWklnJm1VLGxAX3rmtZvyEP3J9nYh7rq8zljPWzcfNUBZ";
	private String accessToken = "1008895205725130752-aCwn5zbTbTAidOWUO4EUv1uRZyhAlm";
	private String accessTokenSecret = "8eqBxoDw7xCbr1OGk9VWgc6Q5o0vrtms9ErtK7RVjenxG";

	@Value("${mail.smtp.auth}")
	private String smtpAuth;
	
	@Value("${mail.smtp.starttls.enable}")
	private String smtpStartTlsEnable;
			
	@Value("${mail.smtp.host}")
	private String smtpHost;
	
	@Value("${mail.smtp.port}")
	private String smtpPort;
	
	@Value("${mail.smtp.ssl.trust}")
	private String smtpSslTrust;
	
	@Value("${gmail.username}")
	private String username;
	
	@Value("${gmail.password}")
	private String password;	
	
	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecretKey() {
		return consumerSecretKey;
	}

	public void setConsumerSecretKey(String consumerSecretKey) {
		this.consumerSecretKey = consumerSecretKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpStartTlsEnable() {
		return smtpStartTlsEnable;
	}

	public void setSmtpStartTlsEnable(String smtpStartTlsEnable) {
		this.smtpStartTlsEnable = smtpStartTlsEnable;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpSslTrust() {
		return smtpSslTrust;
	}

	public void setSmtpSslTrust(String smtpSslTrust) {
		this.smtpSslTrust = smtpSslTrust;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
