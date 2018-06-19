package br.com.poll.pollbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.poll.pollbatch.model.Alternative;

@Repository
public interface AlternativeRepository extends JpaRepository<Alternative, Integer> {

	List<Alternative> findByPollId(Integer pollId);
	
}
