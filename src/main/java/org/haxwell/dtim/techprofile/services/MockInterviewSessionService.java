package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;

public interface MockInterviewSessionService {
	public MockInterviewSession createNewSession();
	
	public Optional<MockInterviewSession> getLastSession();	
}
