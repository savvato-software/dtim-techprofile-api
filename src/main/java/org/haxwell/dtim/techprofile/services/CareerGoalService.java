package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.entities.Question;

public interface CareerGoalService {
	public Optional<CareerGoal> getById(Long id);
	public Iterable<CareerGoal> getAll();
	
	// TODO: Use List instead of Iterable
	public Iterable<Path> getPathsFor(Long id);
	
	public List<Question> getQuestionsForCareerGoal(Long id);
	public CareerGoal save(Long id, String name, String csvPathAssociations);	
}
