package org.haxwell.dtim.techprofile.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class TechProfileTopic {

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
		name="tech_profile_topic_line_item_map"
		, joinColumns={
			@JoinColumn(name="techProfileTopicId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="techProfileLineItemId")
			}
		)
	private Set<TechProfileLineItem> lineItems;
    
    public Set<TechProfileLineItem> getLineItems() {
    	return lineItems;
    }
    
    public void setLineItems(Set<TechProfileLineItem> lineItems) {
    	this.lineItems = lineItems;
    }
    
    @Transient
    private Long sequence;
    
    public Long getSequence() {
    	return sequence;
    }
    
    public void setSequence(Long seq) {
    	this.sequence = seq;
    }
    
	public TechProfileTopic(String name) {
		this.name = name;
	}
	
	public TechProfileTopic() {
		
	}
	
	public String toString() {
		return this.id + " " + this.name;
	}
	
}
