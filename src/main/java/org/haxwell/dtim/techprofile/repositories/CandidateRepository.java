package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Candidate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {

	@Query(nativeQuery = true, value = "select c.* from candidate c where c.name=?1 and (c.phone=?2 or c.email=?3)")
	Optional<Candidate> findByNamePhoneOrEmail(String name, String phone, String email);

	@Query(nativeQuery = true, value = "select c.* from candidate c where c.phone like %?1% or c.email like %?1% LIMIT 1")
	Optional<Candidate> findByPhoneOrEmail(String query);
}
