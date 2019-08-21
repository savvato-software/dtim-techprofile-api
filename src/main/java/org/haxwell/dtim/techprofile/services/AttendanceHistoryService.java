package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;

public interface AttendanceHistoryService {
	public List<UserAttendanceHistory> getAttendanceHistory(Long userId);
}
