package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CareerGoalRepository extends CrudRepository<CareerGoal, Long> {

	Optional<CareerGoal> findById(Long id);
	
	Iterable<CareerGoal> findAll();
	
	@Query(nativeQuery = true, value = "select p.* from path p, career_goal_path_map cgpm where cgpm.careerGoalId=?1 and cgpm.pathId=p.id")
	Iterable<Path> findPathsFor(Long id);
	
	@Query(nativeQuery = true, value = "select q.* from question q where q.id in (SELECT DISTINCT(q.id) from question q, labour_question_map lqm, labour l, milestone_labour_map mlm, milestone m, path_milestone_map plm, path p, career_goal_path_map cgpm, career_goal cg where cg.id=?1 and cgpm.career_goal_id=cg.id and cgpm.path_id=p.id and p.id=plm.path_id and plm.milestone_id=mlm.milestone_id and mlm.labour_id=l.id and l.id=lqm.labour_id and lqm.question_id=q.id)")
	Iterable<Question> findAllQuestionsForCareerGoal(Long id);
}
