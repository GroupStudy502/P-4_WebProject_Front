package com.jmt.board.services;

import com.jmt.board.entities.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ActiveProfiles("test")
public class BoardSaveServiceTest {

    @Autowired
    private BoardSaveService saveService;


    private Board board;

    @Test
    void board() {
        board = new Board();
        board.setBid("freetalk");
        board.setBName("자유게시판");
        /*
         board = Board.builder()
                .bid("freetalk")
                .bName("자유게시판")
                 .gid(UUID.randomUUID().toString())
                 .listAccessType(Authority.ALL)
                 .writeAccessType(Authority.ALL)
                 .commentAccessType(Authority.ALL)
                 .viewAccessType(Authority.ALL)
                 .replyAccessType(Authority.ALL)
                 .locationAfterWriting("list")
                .build();
            */

    }
}