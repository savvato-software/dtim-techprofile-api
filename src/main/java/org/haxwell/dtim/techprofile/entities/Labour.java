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
public class Labour {

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
		name="labour_question_map"
		, joinColumns={
			@JoinColumn(name="labourId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="questionId")
			}
		)
	private Set<Question> questions;
    
    public Set<Question> getQuestions() {
    	return questions;
    }
    
    public void setQuestions(Set<Question> Questions) {
    	this.questions = questions;
    }
    
	public Labour(String name) {
		this.name = name;
	}
	
	public Labour() {
		
	}
}
