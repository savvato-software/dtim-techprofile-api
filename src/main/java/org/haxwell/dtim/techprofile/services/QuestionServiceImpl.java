package org.haxwell.dtim.techprofile.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.UserQuestionGrade;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.haxwell.dtim.techprofile.repositories.UserQuestionGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	UserQuestionGradeRepository userQuestionGradeRepository;
	
	@Override
	public Iterable<Question> getAll(){
		return this.questionRepository.findAll();
	}
	
	@Override
	public Iterable<Question> getByLineItem(Long lineItemId) {
		return this.questionRepository.findByLineItem(lineItemId);
	}
	
	@Override
	public Iterable<Question> getByLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel) {
		return this.questionRepository.findByLineItemAndLevelIndex(lineItemId, lineItemLevel);
	}
	
	@Override
	public Optional<Question> getById(Long id) {
		return this.questionRepository.findById(id);
	}
	
	@Override
	public Iterable<Question> getByTextOrDescription(String query) {
		return this.questionRepository.findByTextOrDescription(query);
	}
	
	@Override
	public Iterable<Question> getQuestionsAnsweredIncorrectly(Long userId) {
		List<UserQuestionGrade> resultList = userQuestionGradeRepository.findByUserIdOrderBySessionIdDesc(userId);

		ArrayList<Long> list = getIds((obj) -> {
			return (UserQuestionGrade)obj; 
		}, resultList, new Long[]{0L, 1L});

		return this.questionRepository.findByIdIn(list);
	}
	
	@Override
	public Iterable<Question> getQuestionsAnsweredCorrectly(Long userId) {
		List<UserQuestionGrade> resultList = userQuestionGradeRepository.findByUserIdOrderBySessionIdDesc(userId);

		ArrayList<Long> list = getIds((obj) -> {
			return (UserQuestionGrade)obj; 
		}, resultList, new Long[]{2L});

		return this.questionRepository.findByIdIn(list);
	}
	
	@Override
	public Iterable<Question> getQuestionsAnsweredIncorrectlyAtAGivenLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel, Long userId) {
		List resultList = em.createNativeQuery("SELECT uqg.* FROM user_question_grade uqg WHERE uqg.user_id=:userId AND (uqg.grade=0 OR uqg.grade=1) AND uqg.question_id IN (SELECT lilqm.question_id FROM line_item_level_question_map lilqm WHERE lilqm.tech_profile_line_item_id=:lineItemId and lilqm.tech_profile_line_item_level_index=:levelIndex) order by uqg.session_id desc;")
				.setParameter("userId", userId)
				.setParameter("lineItemId", lineItemId)
				.setParameter("levelIndex", lineItemLevel)
				.getResultList();

		ArrayList<Long> list = getIds((obj) -> {
			Object[] objArr = (Object[])obj;
			return new UserQuestionGrade( 
					((BigInteger)objArr[0]).longValue(), 
					((BigInteger)objArr[1]).longValue(), 
					((BigInteger)objArr[2]).longValue(), 
					((Integer)objArr[3]).longValue(), 
					(String)objArr[4]);
		}, resultList, new Long[]{0L, 1L});

		return this.questionRepository.findByIdIn(list);
	}
	
	@Override
	public Iterable<Question> getQuestionsAnsweredCorrectlyAtAGivenLineItemAndLevelNumber(Long lineItemId, Long lineItemLevel, Long userId) {
		List resultList = em.createNativeQuery("SELECT uqg.* FROM user_question_grade uqg WHERE uqg.user_id=:userId AND uqg.grade=2 AND uqg.question_id IN (SELECT lilqm.question_id FROM line_item_level_question_map lilqm WHERE lilqm.tech_profile_line_item_id=:lineItemId and lilqm.tech_profile_line_item_level_index=:levelIndex) order by uqg.session_id desc;")
				.setParameter("userId", userId)
				.setParameter("lineItemId", lineItemId)
				.setParameter("levelIndex", lineItemLevel)
				.getResultList();
		
		ArrayList<Long> list = getIds((obj) -> {
			Object[] objArr = (Object[])obj;
			return new UserQuestionGrade( 
					((BigInteger)objArr[0]).longValue(), 
					((BigInteger)objArr[1]).longValue(), 
					((BigInteger)objArr[2]).longValue(), 
					((Integer)objArr[3]).longValue(), 
					(String)objArr[4]);
		}, resultList, new Long[]{2L});

		return this.questionRepository.findByIdIn(list);
	}
	
	// TODO: Delete this.. I think getQuestionsAnsweredIncorrectly(userId) is a better implementation
	//	public List<Question> getAllQuestionsAskedButNotSuccessfullyAnswered(Long userId) {
//
//		// I had to put this here, because when I had it as a Repo query, it didn't seem to be able to convert the array of values that is a row of results in this query, to a Question object. Not sure why, or what I was missing.. I don't rightly remember, but I bet the reason for the other em.createNativeQuery() usages are for a similar reason. Anyway someone should figure out what's going on there one day. 
//		List<Object[]> resultList = em.createNativeQuery("SELECT q.* FROM question q, user_question_grade uqg WHERE uqg.user_id=:userId AND uqg.question_id=q.id AND (uqg.grade=0 OR uqg.grade=1) ORDER BY q.id")
//			.setParameter("userId",  userId)
//			.getResultList();
//		
//		ArrayList<Question> rtn = new ArrayList<>();
//		
//		resultList.forEach(row -> rtn.add(new Question(Long.parseLong(row[0].toString()), row[1].toString(), row[2].toString())));
//		
//		return rtn;
//	}
	
	public List<Question> getAllQuestionsAskedPeriod(Long userId) {
		return this.questionRepository.findAllQuestionsAskedPeriod(userId);
	}
	
	private ArrayList<Long> getIds(Function<Object, UserQuestionGrade> func, List resultList, Long[] validGrades) {
		ArrayList<Long> list = new ArrayList<>();
		Set<Long> ids = new HashSet<>();
		Set<Long> skippedIds = new HashSet<>();
		
		for (int i = 0; i < resultList.size(); i++) {

			UserQuestionGrade uqg = func.apply(resultList.get(i));
			
			Long questionId = uqg.getQuestionId();
			
			if (!skippedIds.contains(questionId)) {
				if (!ids.contains(questionId)) {
					boolean added = false;
					for (int j = 0; j < validGrades.length; j++) {
						if (validGrades[j] == uqg.getGrade()) {
							ids.add(questionId);
							added = true;
						}
					}
					
					if (!added)
						skippedIds.add(questionId);		// we skip this because it says that when asked most recently, this user got the opposite of a valid grade
				}
			}
		}
		
		ids.forEach(id -> list.add(id));
		
		return list;
	}
	
	@Override
	public List getAllLineItemAndLevelsFor(Long questionId) {
		List resultList = em.createNativeQuery("SELECT lilqm.tech_profile_line_item_id, lilqm.tech_profile_line_item_level_index FROM line_item_level_question_map lilqm, question q WHERE q.id=:questionId and lilqm.question_id=q.id;")
				.setParameter("questionId", questionId).getResultList();
		
		return resultList;
	}
	
	@Transactional
	public void setAllLineItemAndLevelsFor(Long questionId, int[][] arr) {
		em.createNativeQuery("DELETE FROM line_item_level_question_map WHERE question_id=:questionId")
			.setParameter("questionId",  questionId).executeUpdate();
		
		int x=0;
		while (x < arr.length) {
			int numUpdated = em.createNativeQuery("INSERT INTO line_item_level_question_map (tech_profile_line_item_id, tech_profile_line_item_level_index, question_id) VALUES (" + arr[x][0] + "," + arr[x][1] + "," + questionId + ");").executeUpdate();
			x++;
		}			
	}

	@Transactional
	public Question save(Long questionId, String questionText, String questionDescription, int[][] lilvassociations) {
		Optional<Question> opt = this.questionRepository.findById(questionId);
		Question q; 
		
		if (opt.isPresent()) {
			q = opt.get();
		} else {
			q = new Question();
		}
		
		q.setText(questionText);
		q.setDescription(questionDescription);
		
		Question savedQ = this.questionRepository.save(q);
		
		this.setAllLineItemAndLevelsFor(savedQ.getId(), lilvassociations);
		
		return q;
	}
}