package org.haxwell.dtim.techprofile.services.attendancehistory;

import java.util.ArrayList;
import java.util.List;

import org.haxwell.dtim.techprofile.entities.MockInterviewSession;

public class AttendanceHistoryAPIResponse {
	MockInterviewSession session;

	public MockInterviewSession getSession() {
		return session;
	}

	public void setSession(MockInterviewSession session) {
		this.session = session;
	}

	List<UserQuestionGradeWithQuestionText> questionGradeList;
	
	public List<UserQuestionGradeWithQuestionText> getQuestionGradeList() {
		if (questionGradeList == null)
			questionGradeList = new ArrayList<>();
		
		return questionGradeList;
	}

	public void setQuestionGradeList(List<UserQuestionGradeWithQuestionText> questionGradeList) {
		this.questionGradeList = questionGradeList;
	}
}
