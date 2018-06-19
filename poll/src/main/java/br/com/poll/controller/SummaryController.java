package br.com.poll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poll.exception.UnauthorizedException;
import br.com.poll.model.Alternative;
import br.com.poll.model.Summary;
import br.com.poll.repository.AlternativeRepository;
import br.com.poll.repository.PollRepository;

@RestController
@RequestMapping("/summary")
public class SummaryController {

	@Autowired
	AlternativeRepository alternativeRepository;

	@Autowired
	PollRepository pollRepository;

	@GetMapping("/results/{pollId}")
	public Summary getResults(@PathVariable(value = "pollId") Integer id,
			@RequestHeader(value = "ownerId") Integer ownerId, @RequestHeader(value = "username") String username,
			@RequestHeader(value = "password") String password) {
		Summary summary = new Summary();
		summary.setPoll(pollRepository.findById(id).get());

		if (summary.getPoll().getOwner().getId() != ownerId || !summary.getPoll().getOwner().getUsername().equals(username)
				|| !summary.getPoll().getOwner().getPassword().equals(password)) {
			throw new UnauthorizedException("summary", "owner", ownerId);
		}

		summary.getPoll().setOwner(null);
		summary.setAlternatives(alternativeRepository.findByPollId(id));
		for (Alternative alternative : summary.getAlternatives()) {
			alternative.setPoll(null);
		}
		return summary;
	}
	
	@GetMapping("/{pollId}")
	public Summary getSummary(@PathVariable(value = "pollId") Integer id) {
		Summary summary = new Summary();
		summary.setPoll(pollRepository.findById(id).get());

		summary.getPoll().setOwner(null);
		summary.setAlternatives(alternativeRepository.findByPollId(id));
		for (Alternative alternative : summary.getAlternatives()) {
			alternative.setPoll(null);
			alternative.setTotal(null);
		}
		return summary;
	}	
	

}
