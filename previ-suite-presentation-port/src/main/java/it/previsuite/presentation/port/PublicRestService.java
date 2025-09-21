package it.previsuite.presentation.port;

import io.smallrye.mutiny.Uni;

import it.previsuite.bean.commons.Headers;
import it.previsuite.bean.response.PingResponseBean;
import it.previsuite.bean.response.PreviResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

public interface PublicRestService {

    @GET
    @Path("ping")
    @Produces(Headers.JSON_UTF8)
    @PermitAll
    Uni<PreviResponse<PingResponseBean>> ping();
}