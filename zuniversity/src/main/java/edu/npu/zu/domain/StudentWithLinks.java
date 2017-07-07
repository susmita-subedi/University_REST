package edu.npu.zu.domain;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import com.fasterxml.jackson.annotation.JsonProperty;

/* This class includes links for HATEOAS
 * Note that we really don't need this class, we could have put our links in the Student class.
*  However, I wanted to start simple.   Only look at this after you fully understand our
*  Student class and how the Rest services work with it.
*/
@XmlRootElement(name = "student")
@InjectLinks({
		@InjectLink(value="student/${instance.id}", rel="self", method="GET"),
})
public class StudentWithLinks extends Student {
	@InjectLinks({
			@InjectLink(value="student/${instance.id}", rel="self", method="GET"),
			@InjectLink(value="student/${instance.id}", rel="edit", method="PUT"),
			@InjectLink(value="student/${instance.id}", rel="delete", method="DELETE"),
			@InjectLink(value="student", rel="list", method="list"),
			@InjectLink(value="student/${instance.id}/inactive", rel="inactive", method="inactive", condition="${instance.active}"),
			@InjectLink(value="student/${instance.id}/active", rel="active", method="active", condition="${not instance.active}")
	})
	private List<Link> links;
	private boolean active;
	
	public StudentWithLinks() {
		
	}
	
	public StudentWithLinks(Student stud) {
		super(stud);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)  /* Needed for inject of links */
	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link")
	@JsonProperty("links")
	@JsonSerialize(using = CustomJsonLinkSerializer.class)      /* How to convert an array of Link to a JSON date string */
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
