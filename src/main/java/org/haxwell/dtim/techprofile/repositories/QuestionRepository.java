package org.haxwell.dtim.techprofile.repositories;

import java.util.List;	

import org.haxwell.dtim.techprofile.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

	// TODO: Can we use List, instead of iterable.. ? WHat we really want is a List...
	
	@Query(nativeQuery = true, value = "select q.* from question q, line_item_level_question_map lilqm where lilqm.tech_profile_line_item_id=?1 and lilqm.tech_profile_line_item_level_index=?2 and lilqm.question_id=q.id")
	Iterable<Question> findByLineItemAndLevelIndex(Long lineItemId, Long levelIndex);
	
	Iterable<Question> findByIdIn(List ids);
	
	@Query(nativeQuery = true, value = "SELECT distinct q.* FROM question q, user_question_grade uqg WHERE uqg.user_id=?1 AND q.id=uqg.question_id ORDER BY q.id")
	List<Question> findAllQuestionsAskedPeriod(Long userId);
	
	@Query(nativeQuery = true, value = "SELECT distinct q.* FROM question q, user_question_grade uqg WHERE uqg.user_id=?1 AND uqg.question_id=q.id AND uqg.grade=0 OR uqg.grade=1 ORDER BY q.id")
	List<Question> findAllQuestionsAskedButNotCorrectlyAnswered(Long userId);
	
	@Query(nativeQuery = true, value = "select q.* from question q, user_question_grade uqg where uqg.user_id = ?1 and uqg.session_id = ?2 and uqg.question_id=q.id")
	List<Question> findAllQuestionsAskedInAGivenSession(Long userId, Long sessionId);
	
}
