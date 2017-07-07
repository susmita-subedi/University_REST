package edu.npu.zu.domain;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Link;

public class CustomJsonLinkSerializer extends JsonSerializer<List<Link>>{

    @Override
    public void serialize(List<Link> links, JsonGenerator jsonGen, SerializerProvider sp) 
            throws IOException, JsonProcessingException {
    	jsonGen.writeStartArray();
		for (Link curLink : links) {
			jsonGen.writeStartObject();
			jsonGen.writeStringField("rel", curLink.getRel());
			jsonGen.writeStringField("href", curLink.getUri().toString());
			jsonGen.writeEndObject();
		}
		jsonGen.writeEndArray();
    }
}