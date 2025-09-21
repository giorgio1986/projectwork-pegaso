package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberQuestionnaireBean;

import java.util.Map;

public interface MemberQuestionnaireManager {
    Uni<MemberQuestionnaireBean> getMemberQuestionnaire(Long memberId);
    Uni<MemberQuestionnaireBean> updateMemberQuestionnaire(Long memberId, Long idQuestionnaire, Map<Integer, String> request);
}