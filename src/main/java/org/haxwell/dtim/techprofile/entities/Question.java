package org.haxwell.dtim.techprofile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

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
	private String text;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	///
	private String description;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	///
	public Question(String text) {
		this.text = text;
	}
	
	public Question(String text, String description) {
		this.text = text;
		this.description = description;
	}
	
	public Question(Long id, String text, String description) {
		this.id = id;
		this.text = text;
		this.description = description;
	}
	
	public Question() {
		
	}
	
	public String toString() {
		return this.id + " " + this.text;
	}
}
