package com.jmt.member.validators;

import com.jmt.member.controllers.RequestId;
import com.jmt.member.entities.Member;
import com.jmt.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestId.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        RequestId form = (RequestId) target;
        String userName = form.getUserName();
        String mobile = form.getMobile();

        // 1. 이름 ,모바일 있는지 체크
        Optional<Member> memberOptional = memberRepository.findByUserNameAndMobile(userName, mobile);
        if (memberOptional.isEmpty()) {
            errors.rejectValue("userName", "Mismatch.user");
        }
    }

    }



