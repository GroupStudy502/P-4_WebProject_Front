package com.jmt.member.services;

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

    public RequestAuthority setAuthority(RequestAuthority form) {
        String authorityName = form.getAuthorityName();
        Long memberSeq = form.getMemberSeq();
        boolean invoke = form.isInvoke();
        System.out.println("==========setAuthority===========");

        Member member = memberRepository.findById(memberSeq).orElse(null);
        if (member != null) {
            List<Authorities> authorities = authoritiesRepository.findByMember(member);
            System.out.println("authorities : " + authorities);
            Set<Authority> _items = authorities.stream().map(Authorities::getAuthority).collect(Collectors.toSet());
            System.out.println("_items : " + _items);

            if(!invoke && _items.contains(Authority.valueOf(authorityName))) { // 권한 삭제
                _items.remove(Authority.valueOf(authorityName));
            }else if(invoke && !_items.contains(Authority.valueOf(authorityName))) { // 권한 부여
                _items.add(Authority.valueOf(authorityName));
            }

            List<Authorities> items = _items.stream().map(a -> Authorities.builder().member(member).authority(a).build())
                    .toList();

            List<Authorities> newAuthorities = authoritiesRepository.saveAllAndFlush(items);
            System.out.println("newAuthorities : " + newAuthorities);

            RequestAuthority newAuthority = RequestAuthority.builder().memberSeq(member.getSeq())
                            .authorityName(authorityName).invoke(invoke).build();

            System.out.println("newAuthority : " + newAuthority);

/*
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            items = authorities.stream().map(a -> Authorities.builder()
                    .member(member)
                    .authority(a)
                    .build()).toList();

            authoritiesRepository.saveAllAndFlush(items);
*/
            //Set<Authority> items = authorities.stream().map(Authorities::getAuthority).collect(Collectors.toSet());
            //authoritiesRepository.
            /*
            Set<Authority> items = authorities.stream().map(Authorities::getAuthority).collect(Collectors.toSet());
            items.add(Authority.valueOf(authorityName));
            List<Authorities> _items = items.stream().map(a -> Authorities.builder().member(member).authority(a).build())
                    .toList();
             */



        }
        return form;
    }


}
