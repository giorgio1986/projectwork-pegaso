package it.previsuite.service.port;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberAddressesBean;
import it.previsuite.bean.request.MemberAddressesRequestBean;

import java.util.List;

public interface MemberAddressesManager {
    Uni<List<MemberAddressesBean>> getMemberAddresses(Long memberId);
    Uni<MemberAddressesBean> addMemberAddress(Long memberId, MemberAddressesRequestBean request);
    Uni<MemberAddressesBean> updateMemberAddress(Long memberId, MemberAddressesBean request);
    Uni<MemberAddressesBean> deleteMemberAddress(Long memberId, Long id);
}