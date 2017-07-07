package edu.npu.zu.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import edu.npu.zu.dao.StudentDaoI;
import edu.npu.zu.domain.Name;
import edu.npu.zu.domain.Student;

@Repository("studentDaoMock")
public class StudentDaoMock implements StudentDaoI {
	private ArrayList<Student> entries = new ArrayList<Student>();
	private long startingId = 100;
	private long nextId = startingId;

	
	public StudentDaoMock() {
		super();
	}

	@PostConstruct  /* Give us some data to test the application -- not for production code */
	private void init() {
		Date curDate = new Date();
		Student stud = null;
		
		Name name = new Name("Bob", "Watson");
		stud = new Student();
		stud.setId(nextId);
		stud.setName(name);
		stud.setAge(24);
		stud.setEnrollDate(curDate);
		entries.add(stud);
		nextId++;
	}
	
	public Student findStudentFromId(long id) {
		for (Student entry: entries) {
			if (entry.hasId(id)) {
				return entry;
			}
		}
		
		return null;
	}
	
	public Student removeStudentWithId(long id) {
		Student stud = findStudentFromId(id);
		if (stud == null) return null;
		
		entries.remove(stud);
		
		return stud;
	}
	
	public Student updateStudentWithId(long id, Student newVersion) {
		Student studentRemoved;
		Date origEnrollDate;
		
		studentRemoved = removeStudentWithId(id);
		if (studentRemoved == null) {
			return null;
		}
		
		/* Not all values are changeable -- use values from the orignal entry */
		newVersion.setId(id);
		origEnrollDate = studentRemoved.getEnrollDate();
		newVersion.setEnrollDate(origEnrollDate);
		
		entries.add(newVersion);
		return newVersion;
	}
	
	public List<Student> findStudents() {
		return entries;
	}
	
	public Student addNewStudent(Student entry) {
		entry.setId(nextId);
		nextId++;
		entries.add(entry);
		return entry;
	}
}
