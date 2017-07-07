package edu.npu.zu.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import edu.npu.zu.domain.Name;
import edu.npu.zu.domain.Student;
import edu.npu.zu.domain.StudentList;

/* This is code that would appear on the Client side.   Remember that before you can run this, you must
 * have your Rest Server running!
 */
public class StudentServicesClient {
	static private Logger logger = Logger.getLogger(StudentServicesClient.class);
	private static String STUDENT_SERVICES_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/student/";
	private static String STUDENT_SERVICES_AUTH_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/authstudent/";
	private static Client authclient=null;  /* Required for JAX-RS authorization processing -- client will add Authorization Header */
	private static Client client=null;  
	
	public static void main(String args[]) {
		testLookupStudent();
//		testLookupStudentWithAuth();
//		testDelete();
//		testPut();
		testPost();
//		testStudentListLookup();
	}
	
	
	/* Client that will add an Authorization header */
	private static Client getClientWithAuth() {
		if (authclient == null) {
			authclient = ClientBuilder.newClient();
			/* Dummy user/password that should be overridden in the actual invocations */
			HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("user", "password");
			authclient.register(authFeature);
		}
		
		return authclient;
	}
	
	/* Client that will not add the authorization header */
	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
		}
		
		return client;
	}
	
	public static Student testLookupStudent() {
		int idToLookup = 100;  /* Just some hardcoded test data */
		int responseCode;
		Student student=null;
		
		Client client = getClient();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(STUDENT_SERVICES_URL + idToLookup);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getAddrEntryInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
		Response response = getAddrEntryInvocation.invoke();
		
		responseCode = response.getStatus();
		logger.info("The response code is: " + responseCode);
		if (responseCode == 200) {
			student = response.readEntity(Student.class);
			logger.info(student);
		}
		
		return student;
	}
	
	/* Use a GET Http Command with an Authorization Header */
	public static void testLookupStudentWithAuth() {
		int idToLookup = 100;  /* Just some hardcoded test data */
		String acctName = "jwilson";  /* Hard coded test data */
		String pswd = "jwabcd";       /* Hard coded test data */
		int responseCode;
		
		/* When using authorization you need a client with the basic authentication registered */
		Client client = getClientWithAuth();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(STUDENT_SERVICES_AUTH_URL + idToLookup);
		Builder requestBuilder = target.request(MediaType.APPLICATION_XML_TYPE);
		/* Use Basic Authorization.  Set the user name and password in the Authorization header */
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, acctName);
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, pswd);
		
		Response response = requestBuilder.get();
		
		responseCode = response.getStatus();
		logger.info("The response code from lookup with Basic Authorization is: " + responseCode);
		if (responseCode == 200) {
			Student student = response.readEntity(Student.class);
			logger.info(student);
		}
	}
	
	public static void testStudentListLookup() {
		int responseCode;
		Client client = getClient();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(STUDENT_SERVICES_URL);
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Builder request = target.request(MediaType.APPLICATION_XML_TYPE);
		Response response = request.get();
		
		responseCode = response.getStatus();
		logger.info("The response code is: " + responseCode);
		if (responseCode == 200) {
			StudentList listOfStudents = response.readEntity(StudentList.class);
			logger.info("Retrieved student list from Http Get request: " + listOfStudents);
		}
	}
	
	/* Using a POST Http Command, we'll add a completely new student */
	public static void testPost() {
		int responseCode;
		Student newStudent;
		Client client = getClient();
		
		newStudent = createNewStudent();
		
		WebTarget target = client.target(STUDENT_SERVICES_URL);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.post(Entity.xml(newStudent));
		
		responseCode = response.getStatus();
		logger.info("The response code from Post operation is: " + responseCode);
		
		if (responseCode == 201) {
			Student createdStudent = response.readEntity(Student.class);
			logger.debug("Student object returned by the POST command: " + createdStudent);
		}
	}
	
	/* Using a PUT Http Command, we'll modify an existing student */
	public static void testPut() {
		int idToModify = 100;  /* Just some hardcoded test data */
		int responseCode;
		Student studentToChange;
		
		/* First lookup our Student object.   Then we'll make a change.   */
		
		Client client = getClient();
		
		studentToChange = testLookupStudent();
		if (studentToChange == null) {
			logger.info("Unable to perform the PUT request.  The lookup of student returned null.  ");
			return;
		}
		
		studentToChange.setAge(32);
		
		WebTarget target = client.target(STUDENT_SERVICES_URL + idToModify);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.put(Entity.xml(studentToChange));
		
		responseCode = response.getStatus();
		logger.info("The response code from Put operation is: " + responseCode);
		
		if (responseCode == 200) {
			Student changedStudent = response.readEntity(Student.class);
			logger.debug("Student changed in PUT request: " + changedStudent);
		}
	}
	
	/* Using a Delete Http Command, we'll delete an existing student */
	public static void testDelete() {
		int idOfStudentToDelete = 100;  /* just some hardcoded test data */
		int responseCode;
		Client client = getClient();
		
		WebTarget target = client.target(STUDENT_SERVICES_URL + idOfStudentToDelete);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.delete();
		
		responseCode = response.getStatus();
		logger.info("The response code from delete operation is: " + responseCode);
		
		if (responseCode == Status.OK.getStatusCode()) {
			logger.debug("Student removed");
		}
	}
	
	public static Student createNewStudent() {
		Name name;
		Student stud;
		
		name = new Name("Walter", "Liu");
		stud = new Student();
		stud.setName(name);
		stud.setAge(23);
		return stud;
	}


}
