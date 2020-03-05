package org.haxwell.dtim.techprofile.services;

import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Milestone;
import org.haxwell.dtim.techprofile.repositories.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MilestoneRepository milestoneRepo;

	public Optional<Milestone> getById(Long id) {
		return milestoneRepo.findById(id);
	}
	
	public Iterable<Milestone> getAll() {
		return milestoneRepo.findAll();
	}

	@Transactional
	public Milestone save(Long milestoneId, String name, String csvLabourAssociations) {
		Optional<Milestone> opt = this.milestoneRepo.findById(milestoneId);
		Milestone m;
		
		if (opt.isPresent()) {
			m = opt.get();
		} else {
			m = new Milestone();
		}
		
		m.setName(name);
		
		m = this.milestoneRepo.save(m);
		
		em.createNativeQuery("DELETE FROM milestone_labour_map WHERE milestone_id=:milestoneId")
		.setParameter("milestoneId", m.getId()).executeUpdate();

		int sequence = 1;
		StringTokenizer st = new StringTokenizer(csvLabourAssociations, ",");
		
		while (st.hasMoreTokens()) {
			em.createNativeQuery("INSERT INTO milestone_labour_map (milestone_id, labour_id, sequence) VALUES ("+ m.getId() + ", "+ st.nextToken() + ", " + sequence++ + ")")
			.executeUpdate();
		}
		
		return this.milestoneRepo.findById(m.getId()).get();
	}
}
