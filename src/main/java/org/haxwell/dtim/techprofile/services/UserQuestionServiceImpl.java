package org.haxwell.dtim.techprofile.services;

import java.util.ArrayList;
import java.util.List;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserQuestionServiceImpl implements UserQuestionService {

	@Autowired
	QuestionService questionService;

	@Autowired
	CareerGoalService careerGoalService;

	@Autowired
	QuestionRepository questionRepository;

	public List<Question> getNextQuestionsForCareerGoal(Long userId, Long careerGoalId, Integer maxToReturn) {
		// select all questions for this user that they have been asked, but not successfully answered.
		List<Question> allQuestionsAskedButNotSuccessfullyAnswered = questionService.getAllQuestionsAskedButNotSuccessfullyAnswered(userId);
		
		// select all questions for this career goal
		List<Question> allQuestionsForThisCareerGoal = careerGoalService.getQuestionsForCareerGoal(careerGoalId);
		
		// The intersection of these sets makes up the first part of our recommended next-questions-to-ask 
		List<Question> firstPart = getIntersection(allQuestionsAskedButNotSuccessfullyAnswered, allQuestionsForThisCareerGoal);
		
		// Next, we select all the questions they've been asked period.
		List<Question> allQuestionsAskedPeriod = questionService.getAllQuestionsAskedPeriod(userId);
		
		// The difference between all the questions that make up this goal, and all that they have been asked before, makes up the second part
		//  of our recommended next-questions-to-ask
		List<Question> secondPart = getDifference(allQuestionsForThisCareerGoal, allQuestionsAskedPeriod);
		
		// now we put it all together
		List<Question> rtn = new ArrayList<>();
		
		rtn.addAll(firstPart.subList(0, Math.min(maxToReturn, firstPart.size())));
		
		if (rtn.size() < maxToReturn) {
			rtn.addAll(secondPart.subList(0, Math.min(maxToReturn - rtn.size(), secondPart.size())));
		}
		
		// TODO: Include Line Item Levels in the response
		//  rtn.foreach questionService.getAllLineItemAndLevelsFor(questionId)
		
		return rtn;
	}

	// TODO: Refactor this so it is testable.. Move getDifference and getIntersection to their own service, and they should then accept List<EntityWithAnIDBehavior>
	private List<Question> getDifference(List<Question> lq1, List<Question> lq2) {
		int index1 = 0;
		int index2 = 0;

		ArrayList<Question> rtn = new ArrayList<>();

		if (lq2.size() > 0) {
			while (index1 < lq1.size() && index2 < lq2.size()) {
				long value1 = lq1.get(index1).getId();
				long value2 = lq2.get(index2).getId();
				
				if (value1 == value2) {
					index1++;
					index2++;
				} else if (value1 > value2) {
					index2++;
				} else if (value1 < value2) {
					rtn.add(lq1.get(index1));
					index1++;
				}
			}
		}
		
		rtn.addAll(lq1.subList(index1, lq1.size()));

		return rtn;
	}
	
	private List<Question> getIntersection(List<Question> lq1, List<Question> lq2) {

		int index1 = 0;
		int index2 = 0;

		ArrayList<Question> rtn = new ArrayList<>();

		while (index1 < lq1.size() && index2 < lq2.size()) {
			long value1 = lq1.get(index1).getId();
			long value2 = lq2.get(index2).getId();
			
			if (value1 == value2) {
				rtn.add(lq1.get(index1));
				index1++;
				index2++;
			} else if (value1 > value2) {
				index1++;
			} else if (value1 < value2) {
				index2++;
			}
		}
		
		return rtn;
	}
	
	public List<Question> getQuestionsAskedInAGivenSession(Long userId, Long sessionId) {
		return questionRepository.findAllQuestionsAskedInAGivenSession(userId, sessionId);
	}
}
