package edu.npu.zu.dao;

import java.util.List;

import edu.npu.zu.domain.Student;

public interface StudentDaoI {
	public Student findStudentFromId(long id);
	public Student addNewStudent(Student student);
	public Student removeStudentWithId(long id);
	public Student updateStudentWithId(long id, Student newVersion); 
	public List<Student> findStudents(); 
}
