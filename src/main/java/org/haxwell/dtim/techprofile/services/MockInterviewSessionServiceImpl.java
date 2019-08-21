package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.repositories.MockInterviewSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockInterviewSessionServiceImpl implements MockInterviewSessionService {

	@Autowired
	MockInterviewSessionRepository mockInterviewSessionRepo;

	public MockInterviewSession createNewSession() {
		MockInterviewSession entity = new MockInterviewSession();
		
		return mockInterviewSessionRepo.save(entity);
	}
	
	public Optional<MockInterviewSession> getLastSession() {
		return mockInterviewSessionRepo.findMostRecentlyStarted();
	}
}
