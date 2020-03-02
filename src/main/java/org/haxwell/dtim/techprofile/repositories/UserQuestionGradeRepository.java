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
	
	List<UserQuestionGrade> findByUserId(Long userId);
}
