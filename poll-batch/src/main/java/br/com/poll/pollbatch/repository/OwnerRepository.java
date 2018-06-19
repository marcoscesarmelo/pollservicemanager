package br.com.poll.pollbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.poll.pollbatch.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}
