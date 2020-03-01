package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Labour;
import org.springframework.data.repository.CrudRepository;

public interface LabourRepository extends CrudRepository<Labour, Long> {

	Optional<Labour> findById(Long id);
	Iterable<Labour> findAll();
}
