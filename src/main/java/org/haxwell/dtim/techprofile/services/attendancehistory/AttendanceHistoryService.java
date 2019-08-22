package org.haxwell.dtim.techprofile.services.attendancehistory;

import java.util.Map;

public interface AttendanceHistoryService {
	public Map<Long, AttendanceHistoryAPIResponse> getAttendanceHistory(Long userId);
}
