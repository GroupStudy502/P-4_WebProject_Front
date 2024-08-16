package com.jmt.board.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.entities.Board;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardConfigInfoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;

    /**
     * 게시판 설정 조회
     * @param bid
     * @return
     */
    public Optional<Board> get(String bid) {
        try {
            String url = utils.adminUrl("/api/board/config" + bid);
            ResponseEntity<JSONData> response = restTemplate.getForEntity(url, JSONData.class);
            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                JSONData jsondata = response.getBody();
                if (!jsondata.isSuccess()) {
                    return Optional.empty();
                }
                Object data = jsondata.getData();
                Board board = om.readValue(om.writeValueAsString(data), Board.class);
                return Optional.ofNullable(board);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
