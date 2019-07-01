package org.haxwell.dtim.techprofile.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MockInterviewSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    private java.sql.Timestamp timestamp;

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }
	
    public void setTimestamp() {
    	this.timestamp = java.sql.Timestamp.from(Calendar.getInstance().toInstant());
    }
    
    public void setTimestamp(java.sql.Timestamp ts) {
    	this.timestamp = ts;
    }

    public MockInterviewSession() {
    	this.setTimestamp();
    }
}