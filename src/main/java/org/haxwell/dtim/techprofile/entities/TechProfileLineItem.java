package org.haxwell.dtim.techprofile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class TechProfileLineItem {

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
	
	///
	private String l0_description;
	
	public String getL0Description() {
		return l0_description;
	}
	
	public void setL0Description(String str) {
		this.l0_description = str;
	}
	
	///
	private String l1_description;
	
	public String getL1Description() {
		return l1_description;
	}
	
	public void setL1Description(String str) {
		this.l1_description = str;
	}
	
	///
	private String l2_description;
	
	public String getL2Description() {
		return l2_description;
	}
	
	public void setL2Description(String str) {
		this.l2_description = str;
	}
	
	///
	private String l3_description;
	
	public String getL3Description() {
		return l3_description;
	}
	
	public void setL3Description(String str) {
		this.l3_description = str;
	}

    @Transient
    private Long sequence;
    
    public Long getSequence() {
    	return sequence;
    }
    
    public void setSequence(Long seq) {
    	this.sequence = seq;
    }

    public TechProfileLineItem(String name, String l0_description, String l1_description, String l2_description, String l3_description) {
		this.name = name;
		this.l0_description = l0_description;
		this.l1_description = l1_description;
		this.l2_description = l2_description;
		this.l3_description = l3_description;
	}
	
	public TechProfileLineItem() {
		
	}
	
	public String toString() {
		return this.id + " " + this.name + " |" + this.l0_description+ " |" + this.l1_description+ " |" + this.l2_description+ " |" + this.l3_description;
	}
}
