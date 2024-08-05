package com.jmt.member.etities;

import com.jmt.member.constants.Authority;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesId {
    private Member member;
    private Authority authority;
}