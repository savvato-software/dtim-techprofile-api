package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.CareerGoal;
import org.haxwell.dtim.techprofile.entities.Path;

public interface PathService {
	public Optional<Path> getById(Long id);
	public Iterable<Path> getAll();
	public Path save(Long pathId, String name, String csvMilestoneAssociations);	
}
