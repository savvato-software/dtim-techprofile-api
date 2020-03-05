package org.haxwell.dtim.techprofile.services;

import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Labour;
import org.haxwell.dtim.techprofile.repositories.LabourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LabourServiceImpl implements LabourService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	LabourRepository labourRepo;

	public Optional<Labour> getById(Long id) {
		return labourRepo.findById(id);
	}
	
	public Iterable<Labour> getAll() {
		return labourRepo.findAll();
	}

	@Transactional
	public Labour save(Long labourId, String name, String csvQuestionAssociations) {
		Optional<Labour> opt = this.labourRepo.findById(labourId);
		Labour l;
		
		if (opt.isPresent()) {
			l = opt.get();
		} else {
			l = new Labour();
		}
		
		l.setName(name);
		
		l = this.labourRepo.save(l);
		
		em.createNativeQuery("DELETE FROM labour_question_map WHERE labour_id=:labourId")
		.setParameter("labourId", l.getId()).executeUpdate();

		int sequence = 1;
		StringTokenizer st = new StringTokenizer(csvQuestionAssociations, ",");
		
		while (st.hasMoreTokens()) {
			em.createNativeQuery("INSERT INTO labour_question_map (labour_id, question_id, sequence) VALUES ("+ l.getId() + ", "+ st.nextToken() + ", " + sequence++ + ")")
			.executeUpdate();
		}
		
		return this.labourRepo.findById(l.getId()).get();
	}
}
