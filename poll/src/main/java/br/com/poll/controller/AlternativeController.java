package br.com.poll.controller;


import br.com.poll.exception.UnauthorizedException;
import br.com.poll.model.Alternative;
import br.com.poll.model.Owner;
import br.com.poll.model.Poll;
import br.com.poll.repository.AlternativeRepository;
import br.com.poll.repository.OwnerRepository;
import br.com.poll.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/alternative")
public class AlternativeController {

	@Autowired
	AlternativeRepository alternativeRepository;
	
    @Autowired
    PollRepository pollRepository;
    
    @Autowired
    OwnerRepository ownerRepository;   

 // Create a new Alternative
    @PostMapping("")
    public Alternative createAlternative(@Valid @RequestBody Alternative alternative,
    		@RequestHeader(value="pollId") Integer pollId,
    		@RequestHeader(value="ownerId") Integer ownerId,
    		@RequestHeader(value="username") String usr,
    		@RequestHeader(value="password") String pwd) {
    	Optional<Owner> owner = ownerRepository.findById(ownerId);
    	if(owner.get() == null || 
    			!owner.get().getUsername().equals(usr) ||
    			!owner.get().getPassword().equals(pwd)) {
    		throw new UnauthorizedException("/alternative", "id",alternative.getPoll().getOwner().getId() );
    	}
    	
    	Poll poll = pollRepository.findById(pollId).get();
    	alternative.setPoll(poll);
    	alternative.setTotal(0);
        Alternative newAlternative =  alternativeRepository.save(alternative);
        newAlternative.setTotal(null);
        newAlternative.getPoll().setOwner(null);
        return newAlternative;
    }
    
    @PutMapping("/vote/{id}")
    public Alternative vote(@PathVariable(value = "id") Integer id) {

    	Alternative alternative = alternativeRepository.findById(id).get();    		
        alternative.setTotal(alternative.getTotal()+ 1);
        Alternative newAlternative = alternativeRepository.save(alternative);
        newAlternative.setTotal(null);
        newAlternative.getPoll().setOwner(null);
        return newAlternative;
    } 
    
    @GetMapping("/{pollId}")
    public List<Alternative> getPollAlternatives(@PathVariable(value = "pollId") Integer id) {
        List<Alternative> alternatives =  alternativeRepository.findByPollId(id);
        for(Alternative a : alternatives) {
        	a.setPoll(null);
        	a.setTotal(null);
        }
        return alternatives;
    }

}
