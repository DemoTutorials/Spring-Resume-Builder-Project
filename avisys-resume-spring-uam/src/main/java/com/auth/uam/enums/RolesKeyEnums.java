package com.auth.uam.enums;


public enum RolesKeyEnums {

	ALL("all"),
	USER("user"),
	ALLUSER("all-user"),
	
	
	POST("role-post"),
	GET("role-get");

	// declaring private variable for getting values
    private String name;
  
    // getter method
    public String getName()
    {
        return this.name;
    }
  
    
    // enum constructor - cannot be public or protected
    private RolesKeyEnums(String name)
    {
        this.name = name;
    }
}
