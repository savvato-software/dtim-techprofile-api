package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;
import org.springframework.data.repository.CrudRepository;

public interface UserTechProfileLineItemScoreRepository extends CrudRepository<UserTechProfileLineItemScore, Long> {

	public List<UserTechProfileLineItemScore> findByUserId(Long userId);
}