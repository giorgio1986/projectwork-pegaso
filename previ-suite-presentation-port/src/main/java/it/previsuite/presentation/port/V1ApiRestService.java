package it.previsuite.presentation.port;

import jakarta.ws.rs.Path;

@Path("/v1")
public interface V1ApiRestService {

    @Path("/public")
    PublicRestService getPublicRestService();

    @Path("/users")
    UsersRestService getUsersRestService();

    @Path("/members/contacts")
    MembersContactsRestService getMembersContactsRestService();

    @Path("/members/addresses")
    MembersAddressesRestService getMembersAddressesRestService();

    @Path("/members/questionnaires")
    MembersQuestionnairesRestService getMembersQuestionnairesRestService();

    @Path("/members/beneficiaries")
    MembersBeneficiariesRestService getMembersBeneficiariesRestService();

    @Path("/members/designatedsubjects")
    MembersDesignatedSubjectsRestService getMembersDesignatedSubjectsRestService();
}