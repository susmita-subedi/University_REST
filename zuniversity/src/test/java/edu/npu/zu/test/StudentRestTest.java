package edu.npu.zu.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Before;
import org.junit.Test;

import edu.npu.zu.domain.Name;
import edu.npu.zu.domain.Student;

/* 
 * As an exercise, write additional tests for the Put and Delete services.
 */
public class StudentRestTest {
	private static String STUDENT_SERVICES_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/student/";
	private static String STUDENT_SERVICES_AUTH_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/authstudent/";
	private Student testStudent;
	
	@Before
	public void init() {
		Name name = new Name("Bob", "Watson");
		testStudent = new Student(); 
		testStudent.setName(name);
		testStudent.setAge(24);
		testStudent.setId(100);
	}
	
	/* Client is Required for JAX-RS authorization processing (user name and password must be provided) */
	private  Client getClientWithAuth() {
		Client client;
		
		client = ClientBuilder.newClient();
		/* Dummy user/password that should be overridden in the actual invocations */
		HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("user", "password");
		client.register(authFeature);
		
		return client;
	}

	@Test
	public  void testLookupStudent() {
		int idToLookup = 100;  /* Just some hardcoded test data */
		int responseCode;
		Student student=null;
		
		Client client = ClientBuilder.newClient();
		
		// Targeting the RESTful Webservice URI we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(STUDENT_SERVICES_URL + idToLookup);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getStudentInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
		Response response = getStudentInvocation.invoke();
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 200);
		
		student = response.readEntity(Student.class);
		assertEquals(student,testStudent);
	}
	
	@Test  
	public  void testLookupStudentWithAuth() {
		int idToLookup = 100;  /* Just some hardcoded test data */
		String acctName = "jwilson";  /* Hard coded test data */
		String pswd = "jwabcd";       /* Hard coded test data */
		int responseCode;
		
		/* When using authorization you need a client with the basic authentication registered */
		Client client = getClientWithAuth();
		
		// WebTarget instance holds the web service URI.
		WebTarget target = client.target(STUDENT_SERVICES_AUTH_URL + idToLookup);
		Builder requestBuilder = target.request(MediaType.APPLICATION_XML_TYPE);  /* we'd like to get back XML from the web service */
		/* Use Basic Authorization.  Set the user name and password in the Authorization header */
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, acctName);
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, pswd);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getStudentRequestInvocation = requestBuilder.buildGet();
		Response response = getStudentRequestInvocation.invoke();
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 200);
		
		Student student = response.readEntity(Student.class);
		assertEquals(student,testStudent);
	}
	
	@Test
	public void testPost() {
		int responseCode;
		Student newStudent = new Student();
		Name name = new Name("Sue", "Tao");
		newStudent = new Student(); 
		newStudent.setName(name);
		newStudent.setAge(21);
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(STUDENT_SERVICES_URL);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.post(Entity.xml(newStudent));
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 201);
		
		Student createdStudent = response.readEntity(Student.class);
		long createdId = createdStudent.getId();
		newStudent.setId(createdId);
		assertEquals(newStudent,createdStudent);
	}
	
}
