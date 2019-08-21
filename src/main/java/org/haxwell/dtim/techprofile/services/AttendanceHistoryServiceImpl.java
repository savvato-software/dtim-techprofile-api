package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;
import org.haxwell.dtim.techprofile.repositories.UserAttendanceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceHistoryServiceImpl implements AttendanceHistoryService {

	@Autowired
	UserAttendanceHistoryRepository uahr;
	
	public List<UserAttendanceHistory> getAttendanceHistory(Long userId) {
		return uahr.findByUserId(userId);
	}
}
