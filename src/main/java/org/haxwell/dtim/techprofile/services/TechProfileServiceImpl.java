package org.haxwell.dtim.techprofile.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.repositories.TechProfileLineItemRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileTopicRepository;
import org.haxwell.dtim.techprofile.repositories.UserTechProfileLineItemScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TechProfileServiceImpl implements TechProfileService {

	private static final Log logger = LogFactory.getLog(TechProfileService.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	TechProfileRepository techProfileRepository;
	
	@Autowired
	TechProfileTopicRepository techProfileTopicRepository;
	
	@Autowired
	TechProfileLineItemRepository techProfileLineItemRepository;
	
	@Autowired
	UserTechProfileLineItemScoreRepository utplisRepository;
	
	@Autowired
	QuestionService questionService;
	
	@Override
	public List<UserTechProfileLineItemScore> getUserIdScores(Long userId) {
		return utplisRepository.findByUserId(userId);
	}

	/**** *** ***/
	public List getQuestionCountsPerCell() {

		// For all of the questions associated with each lineItemLevel
        // where a line item
        // belongs to a topic
        //  that in turn belongs to tech profile
        //  	with id = 1
		//
		//	Count all of the times any question is associated with a given lineItem and Level

		List resultList = em.createNativeQuery("select tech_profile_line_item_id, tech_profile_line_item_level_index, count(*) FROM  (select * from line_item_level_question_map lilqm where lilqm.tech_profile_line_item_id in (select tech_profile_line_item_id from tech_profile_topic_line_item_map tptlim where tptlim.tech_profile_topic_id in (select tech_profile_topic_id from tech_profile_topic_map where tech_profile_id = 1))) as tabl GROUP BY tech_profile_line_item_id, tech_profile_line_item_level_index;")
				.getResultList();
		
		return resultList;
	}

	/**** *** ***/
	public List getCorrectlyAnsweredQuestionCountsPerCell(Long userId) {
		return getQuestionCountsPerCell(userId, (uId) -> { return questionService.getQuestionsAnsweredCorrectly(uId); });
	}

	/**** *** ***/
	public List getIncorrectlyAnsweredQuestionCountsPerCell(Long userId) {	
		return getQuestionCountsPerCell(userId, (uId) -> { return questionService.getQuestionsAnsweredIncorrectly(uId); });
	}

	/**** *** ***/
	public List getAskedQuestionCountsPerCell(Long userId) {	
		List resultList = em.createNativeQuery("select tech_profile_line_item_id, tech_profile_line_item_level_index, count(*) FROM  (select * FROM line_item_level_question_map lilqm WHERE lilqm.question_id IN (SELECT DISTINCT(question_id) FROM user_question_grade WHERE user_id=:userId)) as tabl GROUP BY tech_profile_line_item_id, tech_profile_line_item_level_index;")
				.setParameter("userId", userId)
				.getResultList();

		return resultList;
	}

	private List getQuestionCountsPerCell(Long userId, Function<Long, Iterable<Question>> func) {
		
		/**
		 * Get all the questions for this user via a function
		 * 
		 * For each of those questions, we need its lineitem and level index
		 * 
		 * then count up all that occur for each lineitemlevelindex
		 */
		
		Iterable<Question> questions = func.apply(userId);
		Iterator<Question> iterator = questions.iterator();
		
		HashMap<String, Integer> hashmap = new HashMap<>();
		
		while (iterator.hasNext()) {
			List list = techProfileLineItemRepository.getLineItemLevelIndexForQuestion(iterator.next().getId());
			
			Iterator listIterator = list.iterator();
			while (listIterator.hasNext()) {
				Object[] lilv = (Object[])listIterator.next();
				long lineItemId = ((BigInteger)lilv[0]).longValue();
				long levelIndex = ((BigInteger)lilv[1]).longValue();

				String key = lineItemId+","+levelIndex;

				if (!hashmap.containsKey(key)) 
					hashmap.put(key, 0);

				int count = hashmap.get(key);

				hashmap.put(key, ++count);
			}
		}

		ArrayList<int[]> rtn = new ArrayList<>();

		Iterator<String> keysIterator = hashmap.keySet().iterator();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			int[] intArr = new int[3];
			int commaIndex = key.indexOf(',');

			intArr[0] = Integer.parseInt(key.substring(0, commaIndex));
			intArr[1] = Integer.parseInt(key.substring(commaIndex+1));
			intArr[2] = hashmap.get(key);

			rtn.add(intArr);
		}

		return rtn;
	}
}
