package edu.npu.zu.dao.jdbc;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.npu.zu.dao.StudentDaoI;
import edu.npu.zu.domain.Name;
import edu.npu.zu.domain.Student;

@Repository("studentDaoJdbc")
@Transactional
public class StudentDaoJdbc implements StudentDaoI {
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private StudentRowMapper studentRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		studentRowMapper = new StudentRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource)
		                 .withTableName("student")
		                 .usingGeneratedKeyColumns("id")
		                 .usingColumns("firstname", "lastname", "age", "enrolldate");
	}
	
	@Override
	public Student findStudentFromId(long id) {
		String sql = "SELECT * FROM student WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		List<Student> matchingStudents = dbTemplate.query(sql, params, studentRowMapper);

		if (matchingStudents.size() == 0) {
			return null;
		}
		
		return matchingStudents.get(0);
	}

	/* Complete this method as an exercise. */
	public Student removeStudentWithId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Complete this method as an exercise. */
	@Override
	public Student updateStudentWithId(long id, Student newVersion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> findStudents() {
		String sql = "SELECT * FROM student";
		List<Student> studentList = jdbcTemplate.query(sql, studentRowMapper);
		return studentList;
	}

	private MapSqlParameterSource getStudentParamMap(Student student) {
		Name studName = student.getName();
		MapSqlParameterSource params = new MapSqlParameterSource("id", student.getId());
		params.addValue("age", student.getAge());
		params.addValue("firstname", studName.getFirstName());
		params.addValue("lastname", studName.getLastName());
		params.addValue("enrolldate", student.getEnrollDate());
		return params;
	}
	
	@Override
	public Student addNewStudent(Student student) {
		MapSqlParameterSource params = getStudentParamMap(student);
	    Number newId = jdbcInsert.executeAndReturnKey(params);
	    
	    student.setId(newId.longValue());
	    return student;
	}

}
