package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJms
public class RunApplication  {

    public static void main(String[] args) {
        try {
            System.out.println("Hello world");
            SpringApplication.run(RunApplication.class, args);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}