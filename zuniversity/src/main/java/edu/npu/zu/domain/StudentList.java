package edu.npu.zu.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StudentList implements Serializable {
	private static final long serialVersionUID = 1L;
	// XmlElement sets the name of the entities
	@XmlElement(name = "student")
	private List<Student> sList;

	public StudentList() {
	}
	
	public List<Student> getSList() {
		return sList;
	}

	public void setStudentList(List<Student> newStudList) {
		this.sList = newStudList;
	}
	
	public int numEntries() {
		if (sList == null) return 0;
		return sList.size();
	}
	
	public Student getStudent(int idx) {
		return sList.get(idx);
	}
	
	public String toString() {
		String listStr;
		
		listStr = "StudentList{";
		for (Student entry: sList) {
			listStr = listStr + "\n\t" + entry;
		}
		
		listStr = listStr + "\n}";
		return listStr;
	}
}