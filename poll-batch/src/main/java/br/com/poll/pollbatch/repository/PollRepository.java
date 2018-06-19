package br.com.poll.pollbatch.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.poll.pollbatch.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
	
	List<Poll> findByOwnerIdAndStatus(Integer ownerId, Integer status);
	
	List<Poll> findByStatusAndFinalDateBefore(Integer status, Date finalDate);
	
	@Modifying
	@Transactional
	@Query("update Poll p set p.status = ?1 where p.id = ?2")
	int updatePollStatus(Integer status, Integer id);

}