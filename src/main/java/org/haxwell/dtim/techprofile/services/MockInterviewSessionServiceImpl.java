package org.haxwell.dtim.techprofile.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;
import org.haxwell.dtim.techprofile.repositories.MockInterviewSessionRepository;
import org.haxwell.dtim.techprofile.repositories.UserAttendanceHistoryRepository;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockInterviewSessionServiceImpl implements MockInterviewSessionService {

	@Autowired
	MockInterviewSessionRepository mockInterviewSessionRepo;
	
	@Autowired
	UserAttendanceHistoryRepository userAttendanceHistoryRepo;

	@Autowired
	UserRepository userRepo;

	public MockInterviewSession createNewSession() {
		MockInterviewSession entity = new MockInterviewSession();
		
		return mockInterviewSessionRepo.save(entity);
	}
	
	public Optional<MockInterviewSession> getLastSession() {
		return mockInterviewSessionRepo.findMostRecentlyStarted();
	}
	
	public boolean markInAttendance(Long userId) {
		boolean rtn = false;
		
		Optional<MockInterviewSession> opt = mockInterviewSessionRepo.findMostRecentlyStarted();
		if (opt.isPresent()) {
			rtn = userAttendanceHistoryRepo.save(new UserAttendanceHistory(opt.get().getId(), userId)) != null;
		}
		
		return rtn;
	}
	
	public List<User> getUsersInAttendance() {
		
		List<User> rtn = new ArrayList<>();
		
		List<UserAttendanceHistory> uahList = new ArrayList<>();
		
		Optional<MockInterviewSession> opt = mockInterviewSessionRepo.findMostRecentlyStarted();
		if (opt.isPresent()) {
			uahList = userAttendanceHistoryRepo.findByMockInterviewSessionId(opt.get().getId());
		}

		Iterator<UserAttendanceHistory> iterator = uahList.iterator();
		while (iterator.hasNext()) {
			rtn.add(userRepo.findById(iterator.next().getUserId()).get());
		}
		
		return rtn;
	}
	
}
