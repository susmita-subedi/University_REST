package edu.npu.zu.filters;

import javax.xml.bind.DatatypeConverter;

/* Take the authorization String from the Http Request Header and extract the User name and password */
public class BasicAuthorization {
    public static String[] decode(String auth) {
        //Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
        auth = auth.replaceFirst("[B|b]asic ", "");
 
        //Decode the Base64 into byte[]
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);
 
        // Check to see if the decode failed
        if(decodedBytes == null || decodedBytes.length == 0){
            return null;
        }
 
        // Split the bytes into two strings (login and password) -- use colon as the separator
        return new String(decodedBytes).split(":", 2);
    }
}
