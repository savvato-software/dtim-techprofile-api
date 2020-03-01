package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.Labour;

public interface LabourService {
	public Optional<Labour> getById(Long id);
	public Iterable<Labour> getAll();
}
