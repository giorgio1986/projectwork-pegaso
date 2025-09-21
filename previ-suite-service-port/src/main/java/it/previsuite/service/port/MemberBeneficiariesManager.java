package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberBeneficiaryBaseBean;
import it.previsuite.bean.request.MemberBeneficiariesRequestBean;

import java.util.List;

public interface MemberBeneficiariesManager {
    Uni<List<MemberBeneficiaryBaseBean>> getMemberBeneficiaries(Long memberId);
    Uni<MemberBeneficiaryBaseBean> addMemberBeneficiary(Long memberId, MemberBeneficiariesRequestBean request);
    Uni<MemberBeneficiaryBaseBean> updateMemberBeneficiary(Long memberId, MemberBeneficiaryBaseBean request);
    Uni<MemberBeneficiaryBaseBean> deleteMemberBeneficiary(Long memberId, Long id);
}