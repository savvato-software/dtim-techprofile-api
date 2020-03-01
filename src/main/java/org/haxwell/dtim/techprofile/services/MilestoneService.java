package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Milestone;

public interface MilestoneService {
	public Optional<Milestone> getById(Long id);
	public Iterable<Milestone> getAll();
}
