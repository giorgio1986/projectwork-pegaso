package it.previsuite.presentation.adapter;

import it.previsuite.presentation.port.MembersAddressesRestService;
import it.previsuite.presentation.port.MembersBeneficiariesRestService;
import it.previsuite.presentation.port.MembersDesignatedSubjectsRestService;
import it.previsuite.presentation.port.MembersQuestionnairesRestService;
import it.previsuite.presentation.port.PublicRestService;
import it.previsuite.presentation.port.UsersRestService;
import it.previsuite.presentation.port.V1ApiRestService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class V1ApiRestServiceAdapter implements V1ApiRestService {
    private final PublicRestServiceAdapter publicRestServiceAdapter;
    private final UsersRestServiceAdapter usersRestServiceAdapter;
    private final MembersContactsRestServiceAdapter memberContactsRestServiceAdapter;
    private final MembersAddressesRestServiceAdapter memberAddressesRestServiceAdapter;
    private final MembersQuestionnairesRestServiceAdapter memberQuestionnairesRestServiceAdapter;
    private final MembersBeneficiariesRestServiceAdapter memberBeneficiariesRestServiceAdapter;
    private final MembersDesignatedSubjectsRestServiceAdapter membersDesignatedSubjectsRestServiceAdapter;

    @Inject
    public V1ApiRestServiceAdapter(PublicRestServiceAdapter publicRestServiceAdapter
            , UsersRestServiceAdapter usersRestServiceAdapter
            , MembersContactsRestServiceAdapter memberContactsRestServiceAdapter
            , MembersAddressesRestServiceAdapter memberAddressesRestServiceAdapter
            , MembersQuestionnairesRestServiceAdapter memberQuestionnairesRestServiceAdapter
            , MembersBeneficiariesRestServiceAdapter memberBeneficiariesRestServiceAdapter
            , MembersDesignatedSubjectsRestServiceAdapter membersDesignatedSubjectsRestServiceAdapter
    ) {
        this.publicRestServiceAdapter = publicRestServiceAdapter;
        this.usersRestServiceAdapter = usersRestServiceAdapter;
        this.memberContactsRestServiceAdapter = memberContactsRestServiceAdapter;
        this.memberAddressesRestServiceAdapter = memberAddressesRestServiceAdapter;
        this.memberQuestionnairesRestServiceAdapter = memberQuestionnairesRestServiceAdapter;
        this.memberBeneficiariesRestServiceAdapter = memberBeneficiariesRestServiceAdapter;
        this.membersDesignatedSubjectsRestServiceAdapter = membersDesignatedSubjectsRestServiceAdapter;
    }

    @Override
    public PublicRestService getPublicRestService() {
        return this.publicRestServiceAdapter;
    }

    @Override
    public UsersRestService getUsersRestService() {
        return this.usersRestServiceAdapter;
    }

    @Override
    public MembersContactsRestServiceAdapter getMembersContactsRestService() {
        return this.memberContactsRestServiceAdapter;
    }

    @Override
    public MembersAddressesRestService getMembersAddressesRestService() {
        return this.memberAddressesRestServiceAdapter;
    }

    @Override
    public MembersQuestionnairesRestService getMembersQuestionnairesRestService() {
        return this.memberQuestionnairesRestServiceAdapter;
    }

    @Override
    public MembersBeneficiariesRestService getMembersBeneficiariesRestService() {
        return this.memberBeneficiariesRestServiceAdapter;
    }

    @Override
    public MembersDesignatedSubjectsRestService getMembersDesignatedSubjectsRestService() {
        return this.membersDesignatedSubjectsRestServiceAdapter;
    }
}