package com.jmt.member.controllers;

import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberSearchController {

    @GetMapping("/find_id")
    public JSONData idSearch() {

        return idSearch();
    }

    @GetMapping("/find_pw")
    public JSONData psSearch() {
        return psSearch();
    }
}
