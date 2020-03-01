package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.repositories.CareerGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareerGoalServiceImpl implements CareerGoalService {

	@Autowired
	CareerGoalRepository careerGoalRepo;

	public Optional<CareerGoal> getById(Long id) {
		return careerGoalRepo.findById(id);
	}
	
	public Iterable<CareerGoal> getAll() {
		return careerGoalRepo.findAll();
	}
	
	public Iterable<Path> getPathsFor(Long id) {
		return careerGoalRepo.findPathsFor(id);
	}
}
