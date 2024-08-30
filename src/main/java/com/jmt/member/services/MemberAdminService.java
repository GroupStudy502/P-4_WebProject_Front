package com.jmt.member.services;

import com.jmt.global.rests.JSONData;
import com.jmt.member.constants.Authority;
import com.jmt.member.controllers.RequestAuthority;
import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.Member;
import com.jmt.member.repositories.AuthoritiesRepository;
import com.jmt.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberAdminService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public Member deleteMember(Long seq) {
        Member member = memberRepository.findById(seq).orElse(null);
        if (member != null) {

            //Long delCount = authoritiesRepository.delAllByMember(member);
            //System.out.println("delCount : " + delCount);
            memberRepository.delete(member);
            memberRepository.flush();
            return member;
        }
        return null;
    }

    public JSONData setAuthority(RequestAuthority form) {
        String authorityName = form.getAuthorityName();
        Long memberSeq = form.getMemberSeq();
        boolean invoke = form.isInvoke();

        Member member = memberRepository.findById(memberSeq).orElse(null);
        if (member != null) {
            List<Authorities> authorities = authoritiesRepository.findByMember(member);
            Set<Authority> _items = authorities.stream().map(Authorities::getAuthority).collect(Collectors.toSet());

            //기존 권한 읽어 _items 에 보관 후 _items 에 변경 반영하기
            if(!invoke && _items.contains(Authority.valueOf(authorityName))) { // 권한 삭제
                _items.remove(Authority.valueOf(authorityName));
            }else if(invoke && !_items.contains(Authority.valueOf(authorityName))) { // 권한 부여
                _items.add(Authority.valueOf(authorityName));
            }

            //해당 member의 이전 권한 모두 삭제
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            //_items 으로 부터 새로운 권한 리스트 만들기
            List<Authorities> itemsNew = _items.stream().map(a ->
                            Authorities.builder()
                            .member(member)
                            .authority(a).build()
            ).toList();


            List<Authorities> newAuthorities = authoritiesRepository.saveAllAndFlush(itemsNew);
            System.out.println("newAuthorities : " + newAuthorities);
            JSONData data = new JSONData(newAuthorities);
            data.setMessage("처리되었습니다.");
            data.setSuccess(true);
            return data;
        }
        JSONData data = new JSONData();

        data.setMessage("권한 변경 실패");
        data.setSuccess(false);
        return data;
    }


}
