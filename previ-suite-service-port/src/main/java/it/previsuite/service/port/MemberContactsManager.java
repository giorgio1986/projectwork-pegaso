package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberContactBean;

public interface MemberContactsManager {
    Uni<MemberContactBean> getMemberContacts(Long memberId);
    Uni<MemberContactBean> addMemberContacts(Long memberId, MemberContactBean request);
    Uni<MemberContactBean> updateMemberContacts(Long memberId, MemberContactBean request);
}