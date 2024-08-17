package com.jmt.ai.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiPromptServiceTest {

    @Autowired
    private AiPromptService service;

    @Test
    void test1() {
        service.prompt("today's lunch recommendation, translate to Korean");

    }

}
