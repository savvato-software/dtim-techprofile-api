package org.haxwell.dtim.techprofile.services;

import org.haxwell.dtim.techprofile.entities.CareerGoal;

public interface UserCareerGoalService {
	public CareerGoal getCareerGoalForUser(Long userId);
}
