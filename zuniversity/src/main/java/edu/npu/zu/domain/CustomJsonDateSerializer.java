package edu.npu.zu.domain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/* This class helps Jackson convert a Date object to a formatted date string.
 * This class can be used in the @JsonSerialize annotation to tell
 * Jackson how to do the conversion.
 */
public class CustomJsonDateSerializer extends JsonSerializer<Date> {
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/*  Given a Date object, produce a formatted text string for the date  */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider serProv) throws IOException,
			JsonProcessingException 
	{
		formatter.setTimeZone(TimeZone.getTimeZone( "UTC" ));
		String formattedDate = formatter.format(date);
		gen.writeString(formattedDate);
	}
}
