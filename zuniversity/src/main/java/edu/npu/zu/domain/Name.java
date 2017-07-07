package edu.npu.zu.domain;

public class Name {
	private String lastName;
	private String firstName;
	
	
	public Name() {
	}
	
	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String toString() {
		return "Name[" + "lastName: " + lastName + ", firstName: " + firstName + "]";
	}
	
	public boolean equals(Object tstObj) {
		Name tstName;
		
		if (!(tstObj instanceof Name)) return false;
		tstName = (Name) tstObj;
		
		if (!(tstName.firstName.equals(firstName)) || !(tstName.lastName.equals(lastName))) {
			return false;
		}
		
		return true;
	}

}
