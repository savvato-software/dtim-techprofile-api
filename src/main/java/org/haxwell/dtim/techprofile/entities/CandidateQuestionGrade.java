package org.haxwell.dtim.techprofile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CandidateQuestionGradeId.class)
public class CandidateQuestionGrade {

	@Id
	private Long candidateId;
	
	public Long getCandidateId() {
		return this.candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	@Id
	private Long sessionId;
	
	public Long getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(Long sId) {
		this.sessionId = sId;
	}
	
	@Id
	private Long questionId;
	
	public Long getQuestionId() {
		return this.questionId;
	}
	
	public void setQuestionId(Long qId) {
		this.questionId = qId;
	}
	
	private Long grade;
	
    public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	private String comment;
	
    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CandidateQuestionGrade(Long candidateId, Long sessionId, Long questionId, Long grade, String comment) {
		this.candidateId = candidateId;
		this.sessionId = sessionId;
		this.questionId = questionId;
		this.grade = grade;
		this.comment = comment;
    }

	public CandidateQuestionGrade() {

    }
}
