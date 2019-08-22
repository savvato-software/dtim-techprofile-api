package org.haxwell.dtim.techprofile.services.attendancehistory;

import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;

public class UserQuestionGradeWithQuestionText extends UserQuestionGrade {
	String questionText;

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public UserQuestionGradeWithQuestionText(UserQuestionGrade uqg, String text) {
		this.setUserId(uqg.getUserId());
		this.setSessionId(uqg.getSessionId());
		this.setQuestionId(uqg.getQuestionId());
		this.setGrade(uqg.getGrade());
		
		this.setQuestionText(text);
	}
}
