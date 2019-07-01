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
public class TechProfile {

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
		name="tech_profile_topic_map"
		, joinColumns={
			@JoinColumn(name="techProfileId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="techProfileTopicId")
			}
		)
	private Set<TechProfileTopic> topics;
    
    public Set<TechProfileTopic> getTopics() {
    	return topics;
    }
    
    public void setTopics(Set<TechProfileTopic> topics) {
    	this.topics = topics;
    }
    
	public TechProfile(String name) {
		this.name = name;
	}
	
	public TechProfile() {
		
	}
}
