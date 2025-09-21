package it.previsuite.presentation.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberAddressesBean;
import it.previsuite.bean.commons.Headers;
import it.previsuite.bean.commons.Roles;
import it.previsuite.bean.request.MemberAddressesRequestBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.response.PreviResponseList;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

public interface MembersAddressesRestService {

    @GET
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponseList<MemberAddressesBean>> getMemberAddresses();

    @POST
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberAddressesBean>> addMemberAddress(MemberAddressesRequestBean request);

    @PUT
    @Path("/{id: [0-9]+}")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberAddressesBean>> updateMemberAddress(@PathParam("id") Long id, MemberAddressesRequestBean request);

    @DELETE
    @Path("/{id: [0-9]+}")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberAddressesBean>> deleteMemberAddress(@PathParam("id") Long id);
}