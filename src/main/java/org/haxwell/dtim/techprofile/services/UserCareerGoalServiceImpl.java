package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.repositories.CareerGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserCareerGoalServiceImpl implements UserCareerGoalService {

	@Autowired
	CareerGoalRepository careerGoalRepo;

	public CareerGoal getCareerGoalForUser(Long userId) {
		return careerGoalRepo.findByUserId(userId);
	}
}
