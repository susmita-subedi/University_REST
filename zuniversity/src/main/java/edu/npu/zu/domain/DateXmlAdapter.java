package edu.npu.zu.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/* This class helps JAXB convert a text date to a Date object or
 * convert a Date object to a formatted date string.
 * This class can be used in the @XmlJavaTypeAdapter annotation to tell
 * JAXB how to do the conversion (to/from).
 */
public class DateXmlAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /* Given a Date object, produce a formatted text string  */
    @Override
    public String marshal(Date date) throws Exception {
    	dateFormat.setTimeZone(TimeZone.getTimeZone( "UTC" ));
		String formattedDate = dateFormat.format(date);
		return formattedDate;
    }

    /* Given a formatted text string, produce a Date object */
    @Override
    public Date unmarshal(String v) throws Exception {
    	dateFormat.setTimeZone(TimeZone.getTimeZone( "UTC" ));
        return dateFormat.parse(v);
    }

}
