package org.haxwell.dtim.techprofile.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CandidateAttendanceHistoryId.class)
public class CandidateAttendanceHistory {

	@Id
	private Long mockInterviewSessionId;
	
	public Long getMockInterviewSessionId() {
		return mockInterviewSessionId;
	}
	
	public void setMockInterviewSessionId(Long id) {
		this.mockInterviewSessionId = id;
	}
	
	@Id
	private Long candidateId;
	
	public Long getCandidateId() {
		return this.candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
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

    public CandidateAttendanceHistory(Long mockInterviewSessionId, Long candidateId) {
    	this.mockInterviewSessionId = mockInterviewSessionId;
    	this.candidateId = candidateId;
    	
    	this.setCheckinTimestamp();
    }
    
    public CandidateAttendanceHistory() {
    	this.setCheckinTimestamp();
    }
}
