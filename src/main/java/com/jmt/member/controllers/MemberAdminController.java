package com.jmt.member.controllers;

import com.jmt.global.rests.JSONData;
import com.jmt.member.entities.Member;
import com.jmt.member.services.MemberAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberAdminController {
    private final MemberAdminService service;

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        Member member = service.deleteMember(seq);
        return new JSONData(member);
    }
    @PostMapping("/authorities/save")
    public JSONData save(@RequestParam("requestAuthority") RequestAuthority form) {
        System.out.println("=========" + form);
        //service.setAuthority(form);
        return new JSONData(form);
    }
}
