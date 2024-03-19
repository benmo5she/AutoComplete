package com.bottomline.auto.complete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
This program will provide REST service for 
querying datasource for matching words (e.g start with/auto-complete)
 */
@SpringBootApplication
public class AutoCompleteApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(AutoCompleteApplication.class, args);
    }

}