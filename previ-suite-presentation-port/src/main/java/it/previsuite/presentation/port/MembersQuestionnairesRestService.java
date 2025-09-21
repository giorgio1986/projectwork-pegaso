package it.previsuite.presentation.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberQuestionnaireBean;
import it.previsuite.bean.commons.Headers;
import it.previsuite.bean.commons.Roles;
import it.previsuite.bean.response.PreviResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.Map;

public interface MembersQuestionnairesRestService {

    @GET
    @Path("/")
    @Produces(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberQuestionnaireBean>> getMemberQuestionnaire();

    @PUT
    @Path("/{idQuestionnaire: [0-9]+}")
    @Produces(Headers.JSON_UTF8)
    @Consumes(Headers.JSON_UTF8)
    @RolesAllowed(Roles.REGISTERED)
    Uni<PreviResponse<MemberQuestionnaireBean>> updateMemberQuestionnaire(@PathParam("idQuestionnaire") Long idQuestionnaire, Map<Integer, String> request);
}