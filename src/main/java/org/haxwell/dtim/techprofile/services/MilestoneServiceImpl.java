package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Milestone;
import org.haxwell.dtim.techprofile.repositories.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneServiceImpl implements MilestoneService {

	@Autowired
	MilestoneRepository milestoneRepo;

	public Optional<Milestone> getById(Long id) {
		return milestoneRepo.findById(id);
	}
	
	public Iterable<Milestone> getAll() {
		return milestoneRepo.findAll();
	}
}
