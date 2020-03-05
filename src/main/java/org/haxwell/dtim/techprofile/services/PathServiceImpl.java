package org.haxwell.dtim.techprofile.services;

import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.Path;
import org.haxwell.dtim.techprofile.repositories.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PathServiceImpl implements PathService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	PathRepository pathRepo;

	public Optional<Path> getById(Long id) {
		return pathRepo.findById(id);
	}
	
	public Iterable<Path> getAll() {
		return pathRepo.findAll();
	}
	
	@Transactional
	public Path save(Long pathId, String name, String csvMilestoneAssociations) {
		Optional<Path> opt = this.pathRepo.findById(pathId);
		Path p; 
		
		if (opt.isPresent()) {
			p = opt.get();
		} else {
			p = new Path();
		}
		
		p.setName(name);
		
		p = this.pathRepo.save(p);
		
		em.createNativeQuery("DELETE FROM path_milestone_map WHERE path_id=:pathId")
		.setParameter("pathId",  p.getId()).executeUpdate();

		int sequence = 1;
		StringTokenizer st = new StringTokenizer(csvMilestoneAssociations, ",");
		
		while (st.hasMoreTokens()) {
			em.createNativeQuery("INSERT INTO path_milestone_map (path_id, milestone_id, sequence) VALUES ("+ p.getId() + ", "+ st.nextToken() + ", " + sequence++ + ")")
			.executeUpdate();
		}
		
		return this.pathRepo.findById(p.getId()).get();
	}
}
