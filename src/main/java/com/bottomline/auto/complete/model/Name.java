package com.bottomline.auto.complete.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Name {

    @Id
    private String name;
    
    public Name() {}
    
    public Name(String name) {
    	this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
