package br.com.poll.pollbatch.reader;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.poll.pollbatch.model.Alternative;
import br.com.poll.pollbatch.model.Poll;
import br.com.poll.pollbatch.model.Summary;
import br.com.poll.pollbatch.repository.AlternativeRepository;
import br.com.poll.pollbatch.repository.OwnerRepository;
import br.com.poll.pollbatch.repository.PollRepository;

@Component
public class PollReader {

	
	private final Integer CREATED = 1;
	
	@Autowired
	AlternativeRepository alternativeRepository;

	@Autowired
	PollRepository pollRepository;

	@Autowired
	OwnerRepository ownerRepository;

	public List<Poll> getFinishedPolls() {
		return pollRepository.findByStatusAndFinalDateBefore(CREATED, new Date());
	}
	
	public Summary showSummary(Poll poll) {
		Summary summary = new Summary();
		poll.setOwner(null);
		summary.setPoll(poll);
		summary.setAlternatives(alternativeRepository.findByPollId(poll.getId()));
		for (Alternative alternative : summary.getAlternatives()) {
			alternative.setPoll(null);
		}
		return summary;
	}

}
