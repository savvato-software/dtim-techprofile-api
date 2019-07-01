package org.haxwell.dtim.techprofile.repositories;

import org.haxwell.dtim.techprofile.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

	@Query(nativeQuery = true, value = "select q.* from question q, line_item_level_question_map lilqm where lilqm.tech_profile_line_item_id=?1 and lilqm.tech_profile_line_item_level_index=?2 and lilqm.question_id=q.id")
	Iterable<Question> findByLineItemAndLevelIndex(Long lineItemId, Long levelIndex);
}
