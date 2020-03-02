package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CareerGoalRepository extends CrudRepository<CareerGoal, Long> {

	Optional<CareerGoal> findById(Long id);
	
	Iterable<CareerGoal> findAll();
	
	@Query(nativeQuery = true, value = "select p.* from path p, career_goal_path_map cgpm where cgpm.careerGoalId=?1 and cgpm.pathId=p.id")
	Iterable<Path> findPathsFor(Long id);
	
	@Query(nativeQuery = true, value = "select cg.* from user_career_goal_map ucgm, career_goal cg where cg.id=ucgm.career_goal_id AND ucgm.user_id=?1 LIMIT 1")
	CareerGoal findByUserId(Long userId);
}
