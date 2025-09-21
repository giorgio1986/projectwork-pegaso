package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberDesignatedSubjectsBean;
import it.previsuite.bean.request.MemberDesignatedSubjectsRequestBean;

import java.util.List;

public interface MemberDesignatedSubjectsManager {
    Uni<List<MemberDesignatedSubjectsBean>> getMemberDesignatedSubjects(Long memberId);
    Uni<MemberDesignatedSubjectsBean> addMemberDesignatedSubject(Long memberId, MemberDesignatedSubjectsRequestBean request);
    Uni<MemberDesignatedSubjectsBean> updateMemberDesignatedSubject(Long memberId, MemberDesignatedSubjectsBean request);
    Uni<MemberDesignatedSubjectsBean> deleteMemberDesignatedSubject(Long memberId, Long id);
}