package com.jmt.member.controllers;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAuthority {
    private long memberSeq;
    private String authorityName;
    private boolean invoke;


}
