package br.com.poll.pollbatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.poll.pollbatch.model.Alternative;
import br.com.poll.pollbatch.model.Poll;
import br.com.poll.pollbatch.model.Summary;
import br.com.poll.pollbatch.reader.PollReader;
import br.com.poll.pollbatch.repository.PollRepository;
import br.com.poll.pollbatch.writer.EmailWriter;
import br.com.poll.pollbatch.writer.TwitterWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

	@Autowired
	PollRepository pollRepository;

	@Autowired
	PollReader pollReader;

	@Autowired
	TwitterWriter twitterWriter;

	@Autowired
	EmailWriter emailWriter;	
	
	private final Integer SENT = 2;
	

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedDelay() {
		logger.info("Getting Finished Polls :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		try {
			TimeUnit.SECONDS.sleep(5);
			List<Poll> finishedPolls = pollReader.getFinishedPolls();

			if (finishedPolls.isEmpty()) {
				logger.info("Nothing to process at {}", dateTimeFormatter.format(LocalDateTime.now()));
			} else {
				for (Poll poll : finishedPolls) {
					String to = poll.getOwner().getEmail();
					Summary summary = this.pollReader.showSummary(poll);
					String bodyMessage = this.formatBodyMessage(summary);
					String twitterResult = twitterWriter.tweetResults(bodyMessage);
					logger.info(twitterResult);
					String emailResult = emailWriter.sendMailResults(bodyMessage,to);
					logger.info(emailResult);
					if(twitterResult.startsWith("OK") || emailResult.startsWith("OK")) {
						pollRepository.updatePollStatus(SENT, poll.getId());
					}
				}
			}

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public String formatBodyMessage(Summary summary) {
		StringBuilder str = new StringBuilder();
		str.append("Look our last poll final result: \n").append(summary.getPoll().getDescription() + "\n");
		for (Alternative alternative : summary.getAlternatives()) {
			str.append(alternative.getDescription()).append(": ").append(alternative.getTotal().toString())
					.append("\n");
		}
		return str.toString();
	}

}
