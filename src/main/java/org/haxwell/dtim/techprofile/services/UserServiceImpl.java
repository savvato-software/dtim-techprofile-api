package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.repositories.MockInterviewSessionRepository;
import org.haxwell.dtim.techprofile.repositories.UserAttendanceHistoryRepository;
import org.haxwell.dtim.techprofile.repositories.UserQuestionGradeRepository;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.haxwell.dtim.techprofile.repositories.UserTechProfileLineItemScoreRepository;
import org.haxwell.dtim.techprofile.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserAttendanceHistoryRepository uahRepo;
	
	@Autowired
	MockInterviewSessionRepository misRepo;

	@Autowired
	UserTechProfileLineItemScoreRepository scoreRepo;

	@Autowired
	UserQuestionGradeRepository uqgRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User createNewUser(String name, String password, String phone, String email, String preferredContactMethod) {
		if (password != null && ValidationUtil.isPhoneValid(phone) && ValidationUtil.isEmailValid(email) && name.length() >= 3) {
			
			Optional<User> opt = this.userRepo.findByNamePhoneOrEmail(name, phone, email);
			
			if (!opt.isPresent()) {
				User user = new User(name, passwordEncoder.encode(password), phone, email);
				
				User rtn = userRepo.save(user);
				
				this.sendNewUserSMS();
				this.sendNewUserEmail();
				
				return rtn;
			} else {
				throw new IllegalArgumentException("This user already exists: " + name + " " + phone + " " + email);
			}
		}
		
		return null;
	}
	
	public User updateUserPassword(Long id, String password) {
		if (password != null) {
			
			Optional<User> opt = this.userRepo.findById(id);
			
			if (opt.isPresent()) {
				User user = opt.get();
				
				user.setPassword(passwordEncoder.encode(password));

				User rtn = userRepo.save(user);
				
				return rtn;
			} 
		}
		
		return null;
	}
	
	public User find(String query) {
		Optional<User> opt = userRepo.findByPhoneOrEmail(query);
		User rtn = null;
		
		if (opt.isPresent())
			rtn = opt.get();
		
		return rtn;
	}

	public User findById(Long id) {
		Optional<User> opt = userRepo.findById(id);
		User rtn = null;
		
		if (opt.isPresent())
			rtn = opt.get();
		
		return rtn;
	}
	
	@Override
	public boolean saveScores(List<UserTechProfileLineItemScore> scores) {
		scores.forEach((score) -> { 
			scoreRepo.save(score);
		});

		return true;
	}

	public UserQuestionGrade setGradeForQuestion(Long userId, Long sessionId, Long questionId, Long grade, String comment) {
		UserQuestionGrade uqg = new UserQuestionGrade(userId, sessionId, questionId, grade, comment);
		
		return uqgRepo.save(uqg);
	}

	public List<UserQuestionGrade> getUserQuestionHistory(Long userId, Long questionId) {
		return uqgRepo.getGradesForQuestion(userId, questionId);
	}

	private void sendNewUserSMS() {
		// if the user prefers sms, they can respond to the link in this sms.
		// that will confirm their account, set their preference.
		
		// when they log in next time, we will confirm their attendance by sending an sms.
	}

	private void sendNewUserEmail() {
		// if the user prefers email, they can respond to the link in this email.
		// that will confirm their account, set their preference.
		
		// when they log in next time, we will confirm their attendance by sending an email.
	}
}
