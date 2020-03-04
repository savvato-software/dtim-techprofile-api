package org.haxwell.dtim.techprofile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.repositories.CareerGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CareerGoalServiceImpl implements CareerGoalService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	CareerGoalRepository careerGoalRepo;

	public Optional<CareerGoal> getById(Long id) {
		return careerGoalRepo.findById(id);
	}
	
	public Iterable<CareerGoal> getAll() {
		return careerGoalRepo.findAll();
	}
	
	// TODO: Use List instead of Iterable.. doesn't cost us nothin, and its what we probably want anyway..
	public Iterable<Path> getPathsFor(Long id) {
		return careerGoalRepo.findPathsFor(id);
	}
	
	public List<Question> getQuestionsForCareerGoal(Long id) {
		List<Object[]> resultList = em.createNativeQuery("select q.* from question q where q.id in (SELECT DISTINCT(q1.id) from question q1, labour_question_map lqm, labour l, milestone_labour_map mlm, milestone m, path_milestone_map plm, path p, career_goal_path_map cgpm, career_goal cg where cg.id=:careerGoalId and cgpm.career_goal_id=cg.id and cgpm.path_id=p.id and p.id=plm.path_id and plm.milestone_id=mlm.milestone_id and mlm.labour_id=l.id and l.id=lqm.labour_id and lqm.question_id=q1.id) ORDER BY q.id")
			.setParameter("careerGoalId", id)
			.getResultList();
		
		ArrayList<Question> rtn = new ArrayList<>();
		
		resultList.forEach(row -> rtn.add(new Question(Long.parseLong(row[0].toString()), row[1].toString(), row[2].toString())));
		
		return rtn;
	}
	
	@Transactional
	public CareerGoal save(Long careerGoalId, String name, String csvPathAssociations) {
		Optional<CareerGoal> opt = this.careerGoalRepo.findById(careerGoalId);
		CareerGoal cg; 
		
		if (opt.isPresent()) {
			cg = opt.get();
		} else {
			cg = new CareerGoal();
		}
		
		cg.setName(name);
		
		cg = this.careerGoalRepo.save(cg);
		
		em.createNativeQuery("DELETE FROM career_goal_path_map WHERE career_goal_id=:careerGoalId")
		.setParameter("careerGoalId",  cg.getId()).executeUpdate();

		int sequence = 1;
		StringTokenizer st = new StringTokenizer(csvPathAssociations, ",");
		
		while (st.hasMoreTokens()) {
			em.createNativeQuery("INSERT INTO career_goal_path_map (career_goal_id, path_id, sequence) VALUES ("+ cg.getId() + ", "+ st.nextToken() + ", " + sequence++ + ")")
			.executeUpdate();
		}
		
		return this.careerGoalRepo.findById(cg.getId()).get();
	}
}
