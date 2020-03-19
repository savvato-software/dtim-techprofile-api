package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TechProfileLineItemRepository extends CrudRepository<TechProfileLineItem, Long> {

	@Query(nativeQuery = true, value = "select * from line_item_level_question_map WHERE question_id=?1")
	List getLineItemLevelIndexForQuestion(Long questionId);
}
