package edu.npu.zu.services;

import java.util.List;

import edu.npu.zu.domain.Student;

public interface StudentServiceI {
	public Student getStudentWithId(long id);
	public Student removeStudentWithId(long id);
	public List<Student> getStudentList();
	public Student updateStudent(int id, Student student);
	public Student addStudent(Student student);

}
