package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Path;
import org.springframework.data.repository.CrudRepository;

public interface PathRepository extends CrudRepository<Path, Long> {

	Optional<Path> findById(Long id);
	Iterable<Path> findAll();
}
