package com.avisys.email.enums;

public enum RevisionType {
	Insert("INSERT"),
	Update("UPDATE"),
	Delete("DELETE"),
	Owner("OWNER"),
	TemporaryOwner("TEMPORARY_OWNER");
	
	// declaring private variable for getting values
    private String name;
  
    // getter method
    public String getName()
    {
        return this.name;
    }
  
    // enum constructor - cannot be public or protected
    private RevisionType(String name)
    {
        this.name = name;
    }
}
