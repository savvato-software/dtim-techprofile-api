package org.haxwell.dtim.techprofile.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CandidateAttendanceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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

    public CandidateAttendanceHistory(Long candidateId) {
    	this.candidateId = candidateId;
    }
}
