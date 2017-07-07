package edu.npu.zu.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidAcctExResolver implements ExceptionMapper<InvalidAcctException> {
 
    @Override
    public Response toResponse(InvalidAcctException ex) {
    	ResponseBuilder respBuilder;
        Status httpStatus = Status.UNAUTHORIZED;
        
        respBuilder = Response.status(httpStatus);
        respBuilder.entity(ex.getMessage());
        respBuilder.type(MediaType.TEXT_PLAIN);
        return respBuilder.build();
    }

}