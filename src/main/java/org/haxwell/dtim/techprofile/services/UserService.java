package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;

public interface UserService {
	public User createNewUser(String name, String password, String phone, String email, String preferredContactMethod);
	public User updateUserPassword(Long id, String password);
	
	public boolean saveScores(List<UserTechProfileLineItemScore> scores);
	public UserQuestionGrade setGradeForQuestion(Long userId, Long sessionId, Long questionId, Long grade, String comment);
	
	public List<UserQuestionGrade> getUserQuestionHistory(Long userId, Long questionId);
	
	public User find(String query);
	public User findById(Long id);
}
