package com.jmt.board.services;

import com.jmt.board.entities.BoardData;
import com.jmt.board.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final BoardDataRepository boardDataRepository;

    public List<BoardData> getAllBoardData() {
        return boardDataRepository.findAll();
    }


}
