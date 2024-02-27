package com.springboot.demo.config;

import com.springboot.demo.batch.MyScheduledJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class QuartzConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public static JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(MyScheduledJob.class);
        jobDetailFactoryBean.setDescription("My Quartz Job");
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public static CronTriggerFactoryBean trigger(JobDetail jobDetail) {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(jobDetail);
        // 每天六點執行
        triggerFactoryBean.setCronExpression("0 00 18 * * ?");
        return triggerFactoryBean;
    }
}
