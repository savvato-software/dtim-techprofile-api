package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateAttendanceHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CandidateAttendanceHistoryRepository extends CrudRepository<CandidateAttendanceHistory, Long> {

	@Query(nativeQuery = true, value = "select * from candidate_attendance_history cah where cah.checkin_timestamp >= DATE_SUB(NOW(), INTERVAL 3 HOUR)")
	public List<CandidateAttendanceHistory> getThoseWithinTheLastThreeHours();
}