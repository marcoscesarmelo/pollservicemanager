package br.com.poll.controller;


import br.com.poll.exception.BusinessException;
import br.com.poll.exception.UnauthorizedException;
import br.com.poll.model.Owner;
import br.com.poll.model.Poll;
import br.com.poll.repository.OwnerRepository;
import br.com.poll.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/poll")
public class PollController {
	
	public final Integer CREATED = 1;

    @Autowired
    PollRepository pollRepository;
    
    @Autowired
    OwnerRepository ownerRepository;   

    @GetMapping("/all")
    public List<Poll> getAllPolls() {
        List<Poll> polls =  pollRepository.findAll();
        for(Poll poll : polls) {
        	poll.setOwner(null);
        }
        return polls;
    }

    @PostMapping("")
    public Poll createPoll(@Valid @RequestBody Poll poll,
    		@RequestHeader(value="ownerId") Integer ownerId,
    		@RequestHeader(value="username") String usr,
    		@RequestHeader(value="password") String pwd) {
    	Optional<Owner> owner = ownerRepository.findById(ownerId);
    	if(owner.get() == null || 
    			!owner.get().getUsername().equals(usr) ||
    			!owner.get().getPassword().equals(pwd)) {
    		throw new UnauthorizedException("/poll", "id",poll.getOwner().getId() );
    	}
    	
    	List<Poll> polls = pollRepository.findByOwnerIdAndStatus(ownerId, CREATED);
    	
    	if(polls.size() > 0) {
    		throw new BusinessException("/poll", "mensagem", "Apenas é permitida uma pesquisa aberta por dono");
    	}
    	
    	if(poll.getFinalDate().before(new Date())) {
    		throw new BusinessException("/poll", "mensagem", "Data final deverá ser igual ou maior que a data recente");
    	}
    	
    	poll.setStatus(CREATED);
    	poll.setInitialDate(new Date());
    	poll.setOwner(new Owner(ownerId, usr, pwd, null));
        Poll newPoll =  pollRepository.save(poll);
        
        newPoll.getOwner().setPassword(null);
        return newPoll;
    }

}
