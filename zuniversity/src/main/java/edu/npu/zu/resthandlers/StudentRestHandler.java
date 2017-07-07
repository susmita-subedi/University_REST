package edu.npu.zu.resthandlers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import edu.npu.zu.dao.StudentDaoI;
import edu.npu.zu.dao.mock.StudentDaoMock;
import edu.npu.zu.domain.Student;
import edu.npu.zu.domain.StudentList;
import edu.npu.zu.domain.StudentWithLinks;
import edu.npu.zu.domain.UserAccount;
import edu.npu.zu.exceptions.InvalidAcctException;
import edu.npu.zu.exceptions.UnknownResourceException;
import edu.npu.zu.services.StudentService;
import edu.npu.zu.services.StudentServiceI;
import edu.npu.zu.services.UserService;


@Path("/studrestapp")
public class StudentRestHandler {
	@Autowired
	private StudentServiceI studentService;
	private Logger logger = Logger.getLogger(StudentRestHandler.class);
	
	/* Test Url:
	 * http://localhost:8080/zuniversity/webservices/studrestapp/student/100
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/student/{id}")
	@Produces("application/xml, application/json")
	public Student getStudent(@PathParam("id") int id) {
		Student stud = null;
		
		stud = lookupStudent(id);
		if (stud == null) {
			throw new UnknownResourceException("Student id: " + id + " is invalid");
		}
		
		return stud;
	}
	
	/* This  Url requires a basic Authorization header using an account name and password:
	 * http://localhost:8080/zuniversity/webservices/studrestapp/authstudent/100
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/authstudent/{id}")
	@Produces("application/xml, application/json")
	public Student getStudentWithAuth(@PathParam("id") int id, @HeaderParam("Authorization") String auth) {
		Student stud = null;
		
		logger.debug("Get Request for Student with Authorization Header");
		stud = lookupStudentWithAuth(id, auth);
		return stud;
	}
	
	
	
	/* Return a list of all the students.
	 * Test Url:
	 *      http://localhost:8080/zuniversity/webservices/studrestapp/student
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/student")
	@Produces("application/xml")
	public StudentList getStudentList() {
		List<Student> studList;
		StudentList listOfStudents = new StudentList();
		
		studList = studentService.getStudentList();
		listOfStudents.setStudentList(studList);
		return listOfStudents;
	}
	
	
	/* Test Url -- Post the data from file student.xml (or student.json) to:
	 * http://localhost:8080/zuniversity/webservices/studrestapp/student
	 * After doing the post, use a get command to retrieve the student (and verify that the post was successful).
	 */
	@POST
	@Path("/student")
	@Produces("application/json, application/xml")
	@Consumes("application/json, application/xml")
	public Response addStudent(Student newStudent) {
		ResponseBuilder respBuilder;
		
		studentService.addStudent(newStudent);
		respBuilder = Response.status(Status.CREATED);
		respBuilder.entity(newStudent);
		return respBuilder.build();
	}
	
	/* This method demonstrates Hateoas -- adding hyperlinks to our XML/JSON that tell
	 * the client what the "next" steps might be.   We use a StudentWithLinks object which 
	 * contains extra annotations to get the links.   Note that we could have put that in
	 * our Student class and simply not created the StudentWithLinks class.  However, this 
	 * is a more advanced feature and to focus on only a few things at once, the Student class
	 * was kept simple.
	 * 
	 * Test Url -- Post the data from file student.xml (or student.json) to:
	 * http://localhost:8080/zuniversity/webservices/studrestapp/studentlinks
	 * After doing the post, use a get command to retrieve the student (and verify that the post was successful).
	 */
	@POST
	@Path("/studentlinks")
	@Produces("application/json, application/xml")
	@Consumes("application/json, application/xml")
	public Response addStudentReturnLinks(Student newStudent) {
		ResponseBuilder respBuilder;
		StudentWithLinks studentWithLinks;
		
		studentService.addStudent(newStudent);
		studentWithLinks = new StudentWithLinks(newStudent);
		respBuilder = Response.status(Status.CREATED);
		respBuilder.entity(studentWithLinks);
		return respBuilder.build();
	}
	
	/* This method demonstrates Hateoas -- adding hyperlinks to our XML/JSON that tell
	 * the client what the "next" steps might be.
	 * 
	 * Test Url -- Retrieve the information on a student (includes HATEOAS links)
	 * http://localhost:8080/zuniversity/webservices/studrestapp/studentlinks/100
	 */
	@GET
	@Path("/studentlinks/{id}")
	@Produces("application/json, application/xml")
	@Consumes("application/json, application/xml")
	public StudentWithLinks getStudentReturnLinks(@PathParam("id") int id) {
		Student stud = null;
		StudentWithLinks studWithLinks;
		
		stud = lookupStudent(id);
		if (stud == null) {
			throw new UnknownResourceException("Student id: " + id + " is invalid");
		}
		
		studWithLinks = new StudentWithLinks(stud);
		return studWithLinks;
	}
	
	/* Only return the student if the authentication information is correct */
	private Student lookupStudentWithAuth(long id, String auth) {
		String actualAcctName, expectedAcctName = "jwilson";   /* Hardcoded test data -- replace with database lookup */
		String actualPasswd, expectedPasswd = "jwabcd";        /* Hardcoded test data -- replace with database lookup */

		UserAccount acct = UserService.extractAcctFromAuthorization(auth);
		if (acct == null) {
			logger.debug("Authorization Header was null");
			throw new InvalidAcctException("Invalid Authorization Header");
		}
		
		/*
		 * Need to verify this user account by looking up the account info in
		 * the database. Here we just print it and compare against a "hardcoded"
		 * acct name and password.
		 */
		logger.debug("Authorized user found in lookupStudentWithAuth():  " + acct);
		actualAcctName = acct.getAccountname();
		actualPasswd = acct.getPassword();
		if (!(actualAcctName.equals(expectedAcctName) && actualPasswd
				.equals(expectedPasswd))) {
			throw new InvalidAcctException("Authorization is invalid for account: " + actualAcctName);
		}
		
		return lookupStudent(id);

	}
	
	private Student lookupStudent(long id) {
		Student student;
		
		student = studentService.getStudentWithId(id);
		if (student == null) {
			throw new UnknownResourceException("Student id: " + id + " is invalid");
		}
		
		return student;
	}
	
	/* Test Url -- Put (HTTP Put Command) the data from file student.xml to:
	 * http://localhost:8080/zuniversity/webservices/studrestapp/student/100
	 * After doing the put, use a get command to retrieve the student (and verify that the changes to the original student were made).
	 */
	@PUT
	@Path("/student/{id}")
	@Produces("application/json, application/xml")
	@Consumes("application/json, application/xml")
	public Response updateStudent(@PathParam("id") int id, Student newStudent) {
		ResponseBuilder respBuilder;
		Student updatedStudent;
		
		updatedStudent = studentService.updateStudent(id, newStudent);
		if (updatedStudent == null) {
			respBuilder = Response.status(Status.NOT_FOUND);
		} else {
			respBuilder = Response.status(Status.OK);
			respBuilder.entity(updatedStudent);
		}
		
		return respBuilder.build();
	}
	
	/* Test Url:  Use HTTP Delete command
	 * http://localhost:8080/zuniversity/webservices/studrestapp/student/100
	 */
	@DELETE
	@Path("/student/{id}")
	public Response deleteStudent(@PathParam("id") int id) {
		Student removedStud;
		ResponseBuilder respBuilder;
		
		removedStud = studentService.removeStudentWithId(id);
		if (removedStud == null) {
			respBuilder = Response.status(Status.NOT_FOUND);
		} else {
			respBuilder = Response.ok();
		}
		return respBuilder.build();
	}
}
