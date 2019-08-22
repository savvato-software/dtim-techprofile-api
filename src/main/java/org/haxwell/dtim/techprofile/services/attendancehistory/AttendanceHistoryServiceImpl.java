package org.haxwell.dtim.techprofile.services.attendancehistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.haxwell.dtim.techprofile.controllers.AttendanceHistoryController;
import org.haxwell.dtim.techprofile.entities.MockInterviewSession;
import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.UserAttendanceHistory;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.haxwell.dtim.techprofile.repositories.MockInterviewSessionRepository;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.haxwell.dtim.techprofile.repositories.UserAttendanceHistoryRepository;
import org.haxwell.dtim.techprofile.repositories.UserQuestionGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceHistoryServiceImpl implements AttendanceHistoryService {

	private static final Log logger = LogFactory.getLog(AttendanceHistoryServiceImpl.class);
	
	@Autowired
	UserAttendanceHistoryRepository uahr;
	
	@Autowired
	UserQuestionGradeRepository uqgr;
	
	@Autowired
	QuestionRepository qr;
	
	@Autowired
	MockInterviewSessionRepository misr;
	
	public Map<Long, AttendanceHistoryAPIResponse> getAttendanceHistory(Long userId) {
		List<UserAttendanceHistory> uahList = uahr.findByUserId(userId);
		
		Map<Long, AttendanceHistoryAPIResponse> rtn = new HashMap<Long, AttendanceHistoryAPIResponse>();
		
		if (uahList != null) {
			uahList.forEach((uah) -> {
				AttendanceHistoryAPIResponse resp = rtn.get(uah.getMockInterviewSessionId());
				
				if (resp == null) {
					rtn.put(uah.getMockInterviewSessionId(), new AttendanceHistoryAPIResponse());
					resp = rtn.get(uah.getMockInterviewSessionId());
				}

				Optional<MockInterviewSession> mis = misr.findById(uah.getMockInterviewSessionId());
				
				resp.setSession(mis.get());
			});
			
			List<UserQuestionGrade> uqgList = uqgr.findByUserId(userId);
			if (uqgList != null) {
				uqgList.forEach((uqg) -> {
					AttendanceHistoryAPIResponse resp = rtn.get(uqg.getSessionId());
					
					if (resp == null) {
						logger.error("User " + userId + " has a grade for a question in a session that we do not have them marked as attending (session " + uqg.getSessionId() + "). That's bad!");
					} else {
						Optional<Question> opt = qr.findById(uqg.getQuestionId());

						if (opt.isPresent()) {
							//resp.getQuestionList().add(opt.get());
							resp.getQuestionGradeList().add(new UserQuestionGradeWithQuestionText(uqg, opt.get().getText()));
						}

					}
				});
			}
		}
		
		return rtn;
	}
}
