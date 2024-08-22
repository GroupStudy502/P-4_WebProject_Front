package com.jmt.ai.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.ai.controllers.RequestMessage;
import com.jmt.global.services.ConfigInfoService;
import com.jmt.restaurant.entities.QRestaurant;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.repositories.RestaurantRepository;
import com.jmt.restaurant.services.RestaurantInfoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiPromptService {
    private final ConfigInfoService infoService;
    private final ObjectMapper om;
    private final RestTemplate restTemplate;
    private final RestaurantRepository repository; // 카운트 할 때 필요
    private final JPAQueryFactory queryFactory;
    private final RestaurantInfoService restaurantInfoService;

    public String prompt(String message) {
        Map<String, String> config = infoService.getApiConfig();
        if (config == null || !StringUtils.hasText(config.get("huggingfaceAccessToken"))) {
            return null;
        }

        String token = config.get("huggingfaceAccessToken").trim();

        String url = "https://api-inference.huggingface.co/models/mistralai/Mistral-Nemo-Instruct-2407/v1/chat/completions";

        Map<String, String> data = new HashMap<>();
        data.put("role", "user");
        data.put("content", message);
        RequestMessage params = new RequestMessage(List.of(data));
        try {
            String json = om.writeValueAsString(params);

            HttpHeaders headers = new HttpHeaders();

            System.out.println("token:" + token);
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(URI.create(url), request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> data2 = om.readValue(response.getBody(), new TypeReference<>(){});

                List<Map<String, Object>> data3 = (List<Map<String, Object>>)data2.get("choices");

                Map<String, String> data4 = (Map<String, String>)data3.get(0).get("message");

                String message2 = data4.get("content");
                System.out.println(message2);

                return message2;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Restaurant onePickRestaurant(String message) {


        QRestaurant restaurant = QRestaurant.restaurant;
        BooleanBuilder andBuilder = new BooleanBuilder();

        Restaurant data;
        BooleanExpression condition = null;

        message = message
                .replace("앞", "")
                .replace("역전", "")
                .replace("부근", "")
                .replace("에서", "")
                .replace("가게", "")
                .replace("맛집", "")
                .replace("근처", "")
                .replace("알려줘", "")
                .replace("말해줘", "")
                .replace("알려주세요", "")
                .replace("맛있는", "")
                .replace("식당", "")
                .replace("집", "");

        List<String> arr = Arrays.asList(message.split(" "));
        arr.forEach(a -> {

            BooleanBuilder b = new BooleanBuilder();
            b.and(restaurant.rstrLnnoAdres.concat(restaurant.rstrRdnmAdr).contains(a));
            long total = repository.count(b);

            BooleanBuilder b1 = new BooleanBuilder();
            b1.and(restaurant.reprsntMenuNm.contains(a));
            long total1 = repository.count(b1);

            System.out.println(a + " rstrLnnoAdres count:" +  total + " reprsntMenuNm:" + total1 );

            if(total > 20) { // a : 지명
                andBuilder.and(restaurant.rstrLnnoAdres.concat(restaurant.rstrRdnmAdr).contains(a));
            }
            if(total1 > 2) {
                andBuilder.and(restaurant.reprsntMenuNm.contains(a));
            }

            andBuilder.and(restaurant.rstrNm.concat(restaurant.rstrIntrcnCont).contains(a));
        });

        long total = repository.count(andBuilder);

        System.out.println("total:" + total);

        if (total == 0) { // 없으면 랜덤 으로
            while (true) {
                Long rstrId = Long.valueOf((long) (Math.random() * 2000 + 1000));
                try {
                    data = restaurantInfoService.get(rstrId);  //3693L
                    System.out.println(data);
                }catch (Exception e){
                    continue;
                }
                if (data != null) {
                    break;
                }
            }
            return data;
        }

        // 검색 데이터 처리
        data = queryFactory.selectFrom(restaurant)
                .where(andBuilder) // 검색 조건 후에 추가
                .fetchFirst();

        return data;
    }

}