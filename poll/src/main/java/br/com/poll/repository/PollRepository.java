package br.com.poll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.poll.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
	
	List<Poll> findByOwnerIdAndStatus(Integer ownerId, Integer status);

}