package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.entities.Question;

public interface CareerGoalService {
	public Optional<CareerGoal> getById(Long id);
	public Iterable<CareerGoal> getAll();
	
	public Iterable<Path> getPathsFor(Long id);
	
	public Iterable<Question> getQuestionsForCareerGoal(Long id);
}
