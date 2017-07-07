package edu.npu.zu.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class UnknownResourceExResolver implements ExceptionMapper<UnknownResourceException> {
 
    @Override
    public Response toResponse(UnknownResourceException ex) {
    	ResponseBuilder respBuilder;
        Status httpStatus = Status.NOT_FOUND;
        
        respBuilder = Response.status(httpStatus);
        respBuilder.entity(ex.getMessage());
        respBuilder.type(MediaType.TEXT_PLAIN);
        return respBuilder.build();
    }

}
