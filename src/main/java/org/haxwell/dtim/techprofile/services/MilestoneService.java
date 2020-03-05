package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Milestone;
import org.haxwell.dtim.techprofile.entities.Path;

public interface MilestoneService {
	public Optional<Milestone> getById(Long id);
	public Iterable<Milestone> getAll();
	public Milestone save(Long pathId, String name, String csvMilestoneAssociations);
}
