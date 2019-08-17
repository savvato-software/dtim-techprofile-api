package org.haxwell.dtim.techprofile.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(UserAttendanceHistoryId.class)
public class UserAttendanceHistory {

	@Id
	private Long mockInterviewSessionId;
	
	public Long getMockInterviewSessionId() {
		return mockInterviewSessionId;
	}
	
	public void setMockInterviewSessionId(Long id) {
		this.mockInterviewSessionId = id;
	}
	
	@Id
	private Long userId;
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
    private java.sql.Timestamp checkinTimestamp;

    public java.sql.Timestamp getCheckinTimestamp() {
        return checkinTimestamp;
    }
	
    public void setCheckinTimestamp() {
    	this.checkinTimestamp = java.sql.Timestamp.from(Calendar.getInstance().toInstant());
    }
    
    public void setCheckinTimestamp(java.sql.Timestamp ts) {
    	this.checkinTimestamp = ts;
    }

    public UserAttendanceHistory(Long mockInterviewSessionId, Long userId) {
    	this.mockInterviewSessionId = mockInterviewSessionId;
    	this.userId = userId;
    	
    	this.setCheckinTimestamp();
    }
    
    public UserAttendanceHistory() {
    	this.setCheckinTimestamp();
    }
}
