package org.haxwell.dtim.techprofile.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(UserTechProfileLineItemScoreId.class)
public class UserTechProfileLineItemScore implements Serializable {

	private static final long serialVersionUID = 19844238L;
	
	@Id
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long id) {
		this.userId = id;
	}
	
	@Id
	private Long techProfileLineItemId;
	
	public Long getTechProfileLineItemId() {
		return techProfileLineItemId;
	}
	
	public void setTechProfileLineItemId(Long id) {
		this.techProfileLineItemId = id;
	}
	
	private Long techProfileLineItemScore;
	
	public Long getTechProfileLineItemScore() {
		return techProfileLineItemScore;
	}
	
	public void setTechProfileLineItemScore(Long score) {
		this.techProfileLineItemScore = score;
	}
	
	public UserTechProfileLineItemScore(Long userId, Long techProfileLineItemId, Long techProfileLineItemScore) {
		this.userId = userId;
		this.techProfileLineItemId = techProfileLineItemId;
		this.techProfileLineItemScore = techProfileLineItemScore;
	}
	
	public UserTechProfileLineItemScore() {
		
	}
}
