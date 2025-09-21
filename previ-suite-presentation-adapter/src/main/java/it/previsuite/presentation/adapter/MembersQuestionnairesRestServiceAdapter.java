package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberQuestionnaireBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.presentation.port.MembersQuestionnairesRestService;
import it.previsuite.service.port.MemberQuestionnaireManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ApplicationScoped
public class MembersQuestionnairesRestServiceAdapter extends AbstractServiceAdapter implements MembersQuestionnairesRestService {
    private static final Logger logger = LoggerFactory.getLogger(MembersQuestionnairesRestServiceAdapter.class);
    private final String className = MembersQuestionnairesRestServiceAdapter.class.getName();
    private final MemberQuestionnaireManager memberQuestionnaireManager;
    private final JsonWebToken jwt;

    @Inject
    public MembersQuestionnairesRestServiceAdapter(MemberQuestionnaireManager memberQuestionnaireManager, JsonWebToken jwt) {
        this.memberQuestionnaireManager = memberQuestionnaireManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponse<MemberQuestionnaireBean>> getMemberQuestionnaire() {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberQuestionnaireManager
                        .getMemberQuestionnaire(memberId)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberQuestionnaireBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method getMemberQuestionnaire()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberQuestionnaireBean>> updateMemberQuestionnaire(Long idQuestionnaire, Map<Integer, String> request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method updateMemberQuestionnaire(): request object can not be null", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberQuestionnaireManager
                        .updateMemberQuestionnaire(memberId, idQuestionnaire, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberQuestionnaireBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method updateMemberQuestionnaire()", this.className,  error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}