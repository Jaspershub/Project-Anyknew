package com.springboot.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class NewsApplication {


    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
