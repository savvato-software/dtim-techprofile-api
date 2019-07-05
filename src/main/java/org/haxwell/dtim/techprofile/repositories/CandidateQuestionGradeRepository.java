package org.haxwell.dtim.techprofile.repositories;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.CandidateQuestionGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CandidateQuestionGradeRepository extends CrudRepository<CandidateQuestionGrade, Long> {

	@Query(nativeQuery = true, value = "select cqg.* from candidate_question_grade cqg where cqg.candidate_id = ?1 and cqg.question_id = ?2")	
	List<CandidateQuestionGrade> getGradesForQuestion(Long candidateId, Long questionId);
}
