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
public class CareerGoal {

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
		name="career_goal_path_map"
		, joinColumns={
			@JoinColumn(name="careerGoalId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pathId")
			}
		)
	private Set<Path> paths;
    
    public Set<Path> getPaths() {
    	return paths;
    }
    
    public void setPaths(Set<Path> paths) {
    	this.paths = paths;
    }
    
	public CareerGoal(String name) {
		this.name = name;
	}
	
	public CareerGoal() {
		
	}
}
