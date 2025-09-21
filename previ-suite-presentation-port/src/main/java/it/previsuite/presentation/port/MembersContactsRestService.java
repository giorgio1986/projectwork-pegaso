package it.previsuite.presentation.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberContactBean;
import it.previsuite.bean.commons.Headers;
import it.previsuite.bean.commons.Roles;
import it.previsuite.bean.response.PreviResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


public interface MembersContactsRestService {

    @GET
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberContactBean>> getMemberContacts();

    @POST
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberContactBean>> addMemberContacts(MemberContactBean request);

    @PUT
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberContactBean>> updateMemberContacts(MemberContactBean request);
}