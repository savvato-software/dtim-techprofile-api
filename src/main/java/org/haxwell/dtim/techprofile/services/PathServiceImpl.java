package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.repositories.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathServiceImpl implements PathService {

	@Autowired
	PathRepository pathRepo;

	public Optional<Path> getById(Long id) {
		return pathRepo.findById(id);
	}
	
	public Iterable<Path> getAll() {
		return pathRepo.findAll();
	}
}
