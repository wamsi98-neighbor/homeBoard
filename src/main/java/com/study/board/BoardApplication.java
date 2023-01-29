package com.study.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {

	// 맨 처음에는 JPA와 연동이 안 되어 있기 때문에 DB관련 오류가 뜰 것이다.
	// SpringApplication.run( )을 호출해서 웹 어플일 실행시키는 역할
	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
