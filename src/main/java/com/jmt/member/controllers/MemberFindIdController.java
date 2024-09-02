package com.jmt.member.controllers;

import com.jmt.global.Utils;
import com.jmt.global.exceptions.BadRequestException;
import com.jmt.global.rests.JSONData;
import com.jmt.member.services.MemberFindIdService;
import com.jmt.member.validators.IdValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberFindIdController {

    private final IdValidator idValidator;
    private final Utils utils;
    private final MemberFindIdService memberFindIdService;

    @PostMapping("/find_id")
    public JSONData idSearch(@RequestBody @Valid RequestId form, Errors errors) {


        idValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        String email = memberFindIdService.findId(form);

        return new JSONData(email);
    }

}
