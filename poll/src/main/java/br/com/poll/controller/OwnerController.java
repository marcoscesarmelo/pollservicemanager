package br.com.poll.controller;

import br.com.poll.exception.NotFoundException;
import br.com.poll.exception.UnauthorizedException;
import br.com.poll.model.Owner;
import br.com.poll.repository.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerRepository ownerRepository;

    @GetMapping("/all")
    public List<Owner> getAllOwners() {
        List<Owner> owners =  ownerRepository.findAll();
        for(Owner owner : owners) {
        	owner.setPassword(null);
        }
        return owners;
    }

    @PostMapping("")
    public Owner createOwner(@Valid @RequestBody Owner newOwner, @RequestHeader(value="ownerId") Integer ownerId,
    		@RequestHeader(value="username") String usr,
    		@RequestHeader(value="password") String pwd) {
    	Optional<Owner> owner = ownerRepository.findById(ownerId);
    	if(owner.get() == null || 
    			!owner.get().getUsername().equals(usr) ||
    			!owner.get().getPassword().equals(pwd)) {
    		throw new UnauthorizedException("/owner", "id", ownerId );
    	}
        return ownerRepository.save(newOwner);
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable(value = "id") Integer id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner", "id", id));
    }
 
}
