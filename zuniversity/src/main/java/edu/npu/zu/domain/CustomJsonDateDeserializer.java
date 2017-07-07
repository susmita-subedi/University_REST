package edu.npu.zu.domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/* This class helps Jackson convert a JSON text date to a Date object.
 * This class can be used in the @JsonDeserialize annotation to tell
 * Jackson how to do the conversion.
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Date deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
        String date = parser.getText();
        
        try {
			return dateFormat.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

}
