package com.springboot.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.model.Game;
import com.springboot.demo.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
public class GameController {
    @Autowired(required = false)
    private MybatisService mybatisService;

    @PostMapping("/forex")
    public String forex(@RequestBody Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper(); // 創建 ObjectMapper 實例

        Map<String, Object> response = new HashMap<>(); // 用首購建響應 Map

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String currency = params.get("currency");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Boolean re = validateDateRange(startDate, endDate);
            if (re) {
                Date startdate = sdf.parse(startDate);
                Date enddate = sdf.parse(endDate);
                List<Game> gameList = mybatisService.findGame(startdate, enddate, currency);
                if (gameList == null || gameList.isEmpty()) {
                    response.put("error", Map.of("code", "E002", "message", "沒有找到紀錄"));
                    return mapper.writeValueAsString(response);
                } else {
                    List<Map<String, String>> currencyList = new ArrayList<>();
                    for (Game game : gameList) {
                        Map<String, String> currencyData = new HashMap<>();
                        currencyData.put("date", new SimpleDateFormat("yyyyMMdd").format(game.getDate()));
                        currencyData.put("usd", game.getUsd().toString());
                        currencyList.add(currencyData);
                    }
                    response.put("error", Map.of("code", "0000", "message", "成功"));
                    response.put("currency", currencyList);
                    return mapper.writeValueAsString(response);
                }
            } else {
                response.put("error", Map.of("code", "E001", "message", "日期區間不符"));
                return mapper.writeValueAsString(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Boolean validateDateRange(String startDateStr, String endDateStr) {
        // 定義日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // 解析日期字符串
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        // 計算一年前的日期和昨天的日期
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 檢查日期區間是否符合规则
        if (startDate.isBefore(oneYearAgo) || startDate.isAfter(yesterday) ||
                endDate.isBefore(oneYearAgo) || endDate.isAfter(yesterday) || endDate.isBefore(startDate)) {
            System.err.println("日期區間不符規則");
            return false;
        } else {
            System.err.println("日期區間符合規則");
            return true;
        }
    }
}
