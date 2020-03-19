package org.haxwell.dtim.techprofile.repositories;

import java.util.List;	

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserQuestionGradeRepository extends CrudRepository<UserQuestionGrade, Long> {

	@Query(nativeQuery = true, value = "select uqg.* from user_question_grade uqg where uqg.user_id = ?1 and uqg.question_id = ?2")	
	List<UserQuestionGrade> getGradesForQuestion(Long userId, Long questionId);
	// TODO: will findByUserIdAndQuestionId(...) be an appropriate method name here? so we can remove the nativeQuery?
	//   TODO: On Mar 17 2020 I looked at this again, and think, yes, that would work. So future me, do it. do it now.
	
	List<UserQuestionGrade> findByUserId(Long userId);
	List<UserQuestionGrade> findByUserIdOrderBySessionIdDesc(Long userId);
}
