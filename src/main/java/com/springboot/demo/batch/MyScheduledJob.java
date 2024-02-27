package com.springboot.demo.batch;

import com.springboot.demo.model.Game;
import com.springboot.demo.service.MybatisService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyScheduledJob implements Job {
    private final RestTemplate restTemplate;

    @Autowired
    private MybatisService mybatisService;

    public MyScheduledJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String aa = callAPI();
        JSONArray jsonArray = new JSONArray(aa);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 輸出日期的格式

            Date date = null;
            try {
                date = sdf.parse(jsonObject.getString("Date"));
                String outputDateString = outputFormat.format(date);
                date = outputFormat.parse(outputDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            BigDecimal usdNtd = new BigDecimal(jsonObject.getString("USD/NTD"));
            System.out.println("Date: " + date + ", USD/NTD: " + usdNtd);
            Game game = new Game();
            game.setDate(date);
            game.setUsd(usdNtd);
            game.setType("usd");
            try {
                mybatisService.insertAll(game);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println(aa);
    }

    public String callAPI() {
        String url = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"; // 外部API的URL
        System.out.println(url);
        return restTemplate.getForObject(url, String.class);
    }
}
