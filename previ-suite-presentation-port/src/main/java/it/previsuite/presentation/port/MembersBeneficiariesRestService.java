package it.previsuite.presentation.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberBeneficiaryBaseBean;
import it.previsuite.bean.commons.Headers;
import it.previsuite.bean.commons.Roles;
import it.previsuite.bean.request.MemberBeneficiariesRequestBean;
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


public interface MembersBeneficiariesRestService {

    @GET
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponseList<MemberBeneficiaryBaseBean>> getMemberBeneficiaries();

    @POST
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberBeneficiaryBaseBean>> addMemberBeneficiary(MemberBeneficiariesRequestBean request);

    @PUT
    @Path("/{id: [0-9]+}")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberBeneficiaryBaseBean>> updateMemberBeneficiary(@PathParam("id") Long id, MemberBeneficiariesRequestBean request);

    @DELETE
    @Path("/{id: [0-9]+}")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberBeneficiaryBaseBean>> deleteMemberBeneficiary(@PathParam("id") Long id);
}