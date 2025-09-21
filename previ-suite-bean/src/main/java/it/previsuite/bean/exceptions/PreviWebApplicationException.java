package it.previsuite.bean.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class PreviWebApplicationException extends WebApplicationException {
    public PreviWebApplicationException(Response.Status status, String message) {
        super(Response
                .status(status)
                .entity(message)
                .build()
        );
    }
}