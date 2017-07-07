package edu.npu.zu.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.npu.zu.dao.StudentDaoI;
import edu.npu.zu.dao.mock.StudentDaoMock;
import edu.npu.zu.domain.Student;

@Service
//@Transactional
public class StudentService implements StudentServiceI {
	@Autowired
	//@Qualifier("studentDaoJdbc")  // Use one qualifier or the other, but not both.  Uncomment Transactional annotation when using database
	@Qualifier("studentDaoMock")  
	StudentDaoI studentDao;

	@Override
	public Student getStudentWithId(long id) {
		return studentDao.findStudentFromId(id);
	}

	@Override
	public Student removeStudentWithId(long id) {
		Student removedStud;
		
		removedStud = studentDao.removeStudentWithId(id);
		return removedStud;
	}

	@Override
	public List<Student> getStudentList() {
		return studentDao.findStudents();
	}

	@Override
	public Student updateStudent(int id, Student student) {
		Student updatedStudent;
		
		updatedStudent = studentDao.updateStudentWithId(id, student);
		return updatedStudent;
	}

	@Override
	public Student addStudent(Student student) {
		Date todaysDate = new Date();
		student.setEnrollDate(todaysDate); 
		
		studentDao.addNewStudent(student);
		return student;
	}

	public StudentDaoI getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDaoI studentDao) {
		this.studentDao = studentDao;
	}

}
