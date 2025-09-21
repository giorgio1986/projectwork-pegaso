package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberBeneficiaryBaseBean;
import it.previsuite.bean.MemberBeneficiaryLegalEntityBean;
import it.previsuite.bean.MemberBeneficiaryNaturalEntityBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.MemberBeneficiariesRequestBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.response.PreviResponseList;
import it.previsuite.presentation.port.MembersBeneficiariesRestService;
import it.previsuite.service.port.MemberBeneficiariesManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MembersBeneficiariesRestServiceAdapter extends AbstractServiceAdapter implements MembersBeneficiariesRestService {
    private static final Logger logger = LoggerFactory.getLogger(MembersBeneficiariesRestServiceAdapter.class);
    private final String className = MembersBeneficiariesRestServiceAdapter.class.getName();
    private final MemberBeneficiariesManager memberBeneficiariesManager;
    private final JsonWebToken jwt;

    @Inject
    public MembersBeneficiariesRestServiceAdapter(MemberBeneficiariesManager memberBeneficiariesManager, JsonWebToken jwt) {
        this.memberBeneficiariesManager = memberBeneficiariesManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponseList<MemberBeneficiaryBaseBean>> getMemberBeneficiaries() {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberBeneficiariesManager
                        .getMemberBeneficiaries(memberId)
                        .onItem()
                        .transform(list -> {
                            PreviResponseList<MemberBeneficiaryBaseBean> response = new PreviResponseList<>();
                            response.setData(list);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method getMemberBeneficiaries()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberBeneficiaryBaseBean>> addMemberBeneficiary(MemberBeneficiariesRequestBean request) {
        try {
            String method = "addMemberBeneficiary()";
            this.validateCommonsFields(request, method);

            switch (request.getBeneficiaryType()) {
                case NATURAL_ENTITY -> this.validateNaturalEntityFields(request, method);
                case LEGAL_ENTITY -> this.validateLegalEntityFields(request, method);
            }
        }
        catch (PreviWebApplicationException e) {
            logger.error("[{}] Validation failed on addMemberBeneficiary()", this.className, e);
            return Uni.createFrom().failure(e);
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberBeneficiariesManager
                        .addMemberBeneficiary(memberId, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberBeneficiaryBaseBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method addMemberBeneficiary()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberBeneficiaryBaseBean>> updateMemberBeneficiary(Long id, MemberBeneficiariesRequestBean request) {
        try {
            String method = "updateMemberBeneficiary()";
            this.validateCommonsFields(request, method);

            switch (request.getBeneficiaryType()) {
                case NATURAL_ENTITY -> this.validateNaturalEntityFields(request, method);
                case LEGAL_ENTITY -> this.validateLegalEntityFields(request, method);
            }
        }
        catch (PreviWebApplicationException e) {
            logger.error("[{}] Validation failed on updateMemberBeneficiary()", this.className, e);
            return Uni.createFrom().failure(e);
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> {
                    MemberBeneficiaryBaseBean requestBean = mapper.convertValue(request, MemberBeneficiaryBaseBean.class);
                    requestBean.setId(id);

                    return memberBeneficiariesManager
                            .updateMemberBeneficiary(memberId, requestBean)
                            .onItem()
                            .transform(bean -> {
                                PreviResponse<MemberBeneficiaryBaseBean> response = new PreviResponse<>();
                                response.setData(bean);
                                return response;
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method updateMemberBeneficiary()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberBeneficiaryBaseBean>> deleteMemberBeneficiary(Long id) {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberBeneficiariesManager
                        .deleteMemberBeneficiary(memberId, id)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberBeneficiaryBaseBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method deleteMemberBeneficiary()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    private void validateCommonsFields(MemberBeneficiariesRequestBean request, String method) {
        if (request == null) {
            logger.error("[{}] Invalid request on method {}: request object can not be null", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null");
        }

        if (request.getFiscalCode() == null) {
            logger.error("[{}] Invalid request on method {}: fiscalCode is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: fiscalCode is required");
        }

        if (request.getBeneficiaryType() == null) {
            logger.error("[{}] Invalid request on method {}: beneficiaryType is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiaryType is required");
        }
    }

    private void validateNaturalEntityFields(MemberBeneficiariesRequestBean request, String method) {
        MemberBeneficiaryNaturalEntityBean naturalEntity = mapper.convertValue(request, MemberBeneficiaryNaturalEntityBean.class);

        if (naturalEntity.getName() == null) {
            logger.error("[{}] Invalid request on method {}: name is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: name is required");
        }

        if (naturalEntity.getSurname() == null) {
            logger.error("[{}] Invalid request on method {}: surname is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: surname is required");
        }

        if (naturalEntity.getBirthDate() == null) {
            logger.error("[{}] Invalid request on method {}: birth date is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: birth date is required");
        }

        if (naturalEntity.getGender() == null) {
            logger.error("[{}] Invalid request on method {}: gender is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: gender is required");
        }
    }

    private void validateLegalEntityFields(MemberBeneficiariesRequestBean request, String method) {
        MemberBeneficiaryLegalEntityBean legalEntity = mapper.convertValue(request, MemberBeneficiaryLegalEntityBean.class);

        if (legalEntity.getCompanyName() == null) {
            logger.error("[{}] Invalid request on method {}: company name is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: company name is required");
        }

        if (legalEntity.getVatNumber() == null) {
            logger.error("[{}] Invalid request on method {}: vat number is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: vat number is required");
        }

        if (legalEntity.getNation() == null) {
            logger.error("[{}] Invalid request on method {}: nation is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: nation is required");
        }

        if (legalEntity.getProvince() == null) {
            logger.error("[{}] Invalid request on method {}: province is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: province is required");
        }

        if (legalEntity.getLocation() == null) {
            logger.error("[{}] Invalid request on method {}: location is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: location is required");
        }

        if (legalEntity.getAddress() == null) {
            logger.error("[{}] Invalid request on method {}: address is required", this.className, method);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: address is required");
        }
    }
}