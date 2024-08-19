package com.jmt.board.services;

import com.jmt.board.entities.BoardData;
import com.jmt.board.entities.QBoardData;
import com.jmt.board.repositories.BoardDataRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional  //EntityManager 쓰기위함
@RequiredArgsConstructor
public class BoardInfoService {

    private final JPAQueryFactory jpaQueryFactory;
    private final BoardDataRepository repository;

    public List<BoardData> getAllBoardData() {
        QBoardData boardData = QBoardData.boardData;

        List<BoardData> items = jpaQueryFactory.selectFrom(boardData)
                .leftJoin(boardData.board)
                .fetchJoin()
                .leftJoin(boardData.member)
                .fetchJoin()
                .fetch();
        return items;
    }

    public List<BoardData> gelList() {

        return null;
    }

    public BoardData get(Long seq) {
        return null;
    }
}
