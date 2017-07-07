package edu.npu.zu.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink;

@XmlRootElement(name = "student")
public class Student {
	private long id;
	private Name name;
	private int age;
	/* See annotations below on the getEnrollDate() method for converting to/from Date objects */
	private Date enrollDate;
	
	public Student() {
	}
	
	public Student(Student studToCopy) {
		this.id = studToCopy.id;
		this.name = studToCopy.name;
		this.age = studToCopy.age;
		this.enrollDate = studToCopy.enrollDate;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public String toString() {
		return "Student[id: " + id + ", " + name + ", age: " + age + "]";
	}
	
	public boolean hasId(long id) {
		return this.id == id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	/* Note: the @XmlJavaTypeAdapter (for JAXB) only works when placed on the get() function.  For Jackson (JSON), you don't have
	 * to do this, you could put it on the field (data member) instead.
	 */
	@XmlElement(name = "enrollDate", required = true) 
	@XmlJavaTypeAdapter(DateXmlAdapter.class)  // This class has methods to convert a date to/from XML in the format we want
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)  /* How to convert a json date string to a date object */
	@JsonSerialize(using = CustomJsonDateSerializer.class)      /* How to convert a date object to a JSON date string */
	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	public boolean equals(Object tstObj) {
		Student tstStud;
		
		if (!(tstObj instanceof Student)) return false;
		tstStud = (Student) tstObj;
		
		if ((tstStud.id != id) || !(tstStud.name.equals(name)) || (tstStud.age != age)) {
			return false;
		}
		
		return true;
	}
	
}
