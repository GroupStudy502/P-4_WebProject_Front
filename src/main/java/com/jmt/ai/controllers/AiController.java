package com.jmt.ai.controllers;

import com.jmt.ai.services.AiPromptService;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.global.services.ConfigInfoService;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiPromptService service;
    private final RestaurantInfoService infoService;
    private final Utils utils;
    private final ConfigInfoService configInfoService;

    @GetMapping
    public JSONData index(@RequestParam("message") String message) {
        Map<String, String> config =  configInfoService.getApiConfig();

        System.out.println(config);
        if (config == null || !StringUtils.hasText(config.get("useHuggingFace"))) {
            return null;
        }
        System.out.println(config.get("useHuggingFace"));
        if (config.get("useHuggingFace").equals("true")) {

            String response = service.prompt(message);

            System.out.println(response);
            JSONData jsonData = new JSONData(response);
            jsonData.setSuccess(response != null);
            System.out.println(jsonData.getData());
            return jsonData;
        }
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
                "<a href='url/" + data.getRstrId() + "'>" +
                data.getRstrNm() + "</a> " +
                data.getRstrRdnmAdr() + " " +
                (data.getRstrIntrcnCont() != null ? data.getRstrIntrcnCont() : "") + " " +
                (data.getRstrTelNo() != null ? data.getRstrTelNo() : "")
        );
    }
}