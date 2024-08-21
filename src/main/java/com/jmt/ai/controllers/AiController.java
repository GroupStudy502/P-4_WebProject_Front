package com.jmt.ai.controllers;

import com.jmt.ai.services.AiPromptService;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiPromptService service;
    private final RestaurantInfoService infoService;
    private final Utils utils;

    @GetMapping
    public JSONData index(@RequestParam("message") String message) {
        /*
        String response = service.prompt(message);

        System.out.println(response);
        JSONData jsonData = new JSONData(response);
        jsonData.setSuccess(response != null);
        System.out.println(jsonData.getData());
        return jsonData;
         */
        Restaurant data;
        while (true) {
            Long rstrId = Long.valueOf((long) (Math.random() * 2000 + 1000));
            try {
                data = infoService.get(rstrId);
            }catch (Exception e){
                continue;
            }
            if (data != null) {
                break;
            }
        }

        return new JSONData(
                "<Link to='url/" + data.getRstrId() + "'>" +
                data.getRstrNm() + "</Link> " +
                data.getRstrRdnmAdr() + " " +
                (data.getRstrIntrcnCont() != null ? data.getRstrIntrcnCont() : "") + " " +
                (data.getRstrTelNo() != null ? data.getRstrTelNo() : "")
        );
    }
}