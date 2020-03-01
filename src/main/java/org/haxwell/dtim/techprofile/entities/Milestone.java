package org.haxwell.dtim.techprofile.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Milestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	///
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    @ManyToMany
	@JoinTable(
		name="milestone_labour_map"
		, joinColumns={
			@JoinColumn(name="milestoneId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="labourId")
			}
		)
	private Set<Labour> labours;
    
    public Set<Labour> getLabours() {
    	return labours;
    }
    
    public void setLabours(Set<Labour> labours) {
    	this.labours = labours;
    }
    
	public Milestone(String name) {
		this.name = name;
	}
	
	public Milestone() {
		
	}
}
