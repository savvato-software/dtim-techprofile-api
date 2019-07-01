package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MockInterviewSessionRepository extends CrudRepository<MockInterviewSession, Long> {

	@Query(nativeQuery = true, value = "select m.* from mock_interview_session m ORDER BY m.timestamp DESC LIMIT 1")
	Optional<MockInterviewSession> findMostRecentlyStarted();
}
