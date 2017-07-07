package edu.npu.zu.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import edu.npu.zu.domain.Name;
import edu.npu.zu.domain.Student;

public class StudentRowMapper implements RowMapper<Student> {

	public Student mapRow(ResultSet resultSet, int row) throws SQLException {
		String lastName, firstName;
		int age, id;
		Student stud;
		Name name;
		Date enrollDate;
		
		lastName = resultSet.getString("lastname");
		firstName = resultSet.getString("firstname");
		name = new Name(firstName, lastName);
		age = resultSet.getInt("age");
		id = resultSet.getInt("id");
		enrollDate = resultSet.getDate("enrolldate");
		
		stud = new Student();
		stud.setName(name);
		stud.setAge(age);
		stud.setId(id);
		stud.setEnrollDate(enrollDate);
		
		return stud;
	}

}
