package org.haxwell.dtim.techprofile.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public Iterable<Question> getAll(){
		return this.questionRepository.findAll();
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
	public List getAllLineItemAndLevelsFor(Long questionId) {
		List resultList = em.createNativeQuery("SELECT lilqm.tech_profile_line_item_id, lilqm.tech_profile_line_item_level_index FROM line_item_level_question_map lilqm, question q WHERE q.id=:questionId and lilqm.question_id=q.id;")
				.setParameter("questionId", questionId).getResultList();
		
		return resultList;
	}
	
	@Transactional
	public void setAllLineItemAndLevelsFor(Long questionId, int[][] arr, int count) {
		em.createNativeQuery("DELETE FROM line_item_level_question_map WHERE question_id=:questionId")
			.setParameter("questionId",  questionId).executeUpdate();
		
		int x=0;
		while (x < count) {
			int numUpdated = em.createNativeQuery("INSERT INTO line_item_level_question_map (tech_profile_line_item_id, tech_profile_line_item_level_index, question_id) VALUES (" + arr[x][0] + "," + arr[x][1] + "," + questionId + ");").executeUpdate();
			x++;
		}			
	}
}