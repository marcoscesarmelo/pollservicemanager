package br.com.poll.pollbatch.writer;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.poll.pollbatch.config.WriterConfig;

@Component
public class TwitterWriter {

	private String message;

	@Autowired
	WriterConfig writerConfig;

	public String tweetResults(String bodyMessage) {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(writerConfig.getConsumerKey(), writerConfig.getConsumerSecretKey());
			AccessToken accessToken = new AccessToken(writerConfig.getAccessToken(), writerConfig.getAccessTokenSecret());
			twitter.setOAuthAccessToken(accessToken);
			this.setMessage(bodyMessage);			
			twitter.updateStatus(message);
			return "OK, Tweet successfuly!";
		} catch (Exception e) {		
			return "Something wrong occurred! " + e.getMessage();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
