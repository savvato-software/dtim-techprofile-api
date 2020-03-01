package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Milestone;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {

	Optional<Milestone> findById(Long id);
	Iterable<Milestone> findAll();
}
