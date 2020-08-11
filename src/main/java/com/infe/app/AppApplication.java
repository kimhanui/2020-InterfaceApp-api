package com.infe.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//스프링부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정함.
//이 어노테이션 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 root dir에 위치해야한다.
//이 어노테이션으로 내장 WAS를 실행함.
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}