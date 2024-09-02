package com.jmt.member.services;

import com.jmt.member.controllers.RequestId;
import com.jmt.member.entities.Member;
import com.jmt.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFindIdService {

    private final MemberRepository memberRepository;
 //이메일 찾기
    public String findId(RequestId form) {

        String userName = form.getUserName();

        Optional<Member> memberOptional = memberRepository.findByUserName(userName);


        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            return member.getEmail();
        } else {
            return "에러";
        }
    }
}