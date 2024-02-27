package com.springboot.demo;

import com.springboot.demo.batch.MyScheduledJob;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BatchTest {

    @Mock
    private JobExecutionContext jobExecutionContext;

//    @MockBean
//    private MybatisService mybatisService;
    @Autowired
    private MyScheduledJob myScheduledJob;

    @Test
    public void testExecute() throws Exception {
        // 调用 execute 方法
        myScheduledJob.execute(jobExecutionContext);

    }
}