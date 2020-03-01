package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Labour;
import org.haxwell.dtim.techprofile.repositories.LabourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabourServiceImpl implements LabourService {

	@Autowired
	LabourRepository labourRepo;

	public Optional<Labour> getById(Long id) {
		return labourRepo.findById(id);
	}
	
	public Iterable<Labour> getAll() {
		return labourRepo.findAll();
	}
}
