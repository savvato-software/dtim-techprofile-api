package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserAttendanceHistoryRepository extends CrudRepository<UserAttendanceHistory, Long> {

	@Query(nativeQuery = true, value = "select * from user_attendance_history uah where uah.checkin_timestamp >= DATE_SUB(NOW(), INTERVAL 3 HOUR)")
	public List<UserAttendanceHistory> getThoseWithinTheLastThreeHours();

	public List<UserAttendanceHistory> findByUserId(Long userId);
}