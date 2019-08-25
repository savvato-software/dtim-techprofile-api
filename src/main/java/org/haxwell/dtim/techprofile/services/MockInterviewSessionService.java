package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.entities.User;

public interface MockInterviewSessionService {
	public MockInterviewSession createNewSession();
	
	public Optional<MockInterviewSession> getLastSession();
	
	public boolean markInAttendance(Long userId);
	public List<User> getUsersInAttendance();
	
}
