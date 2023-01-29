package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // table을 의미
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    /* Id는 해당 컬럼이 PK임을 나타냄
       GeneratedValue는 주키의 값을 위한 자동생성 전략을 명시
       IDENTITY는 기본 키 생성을 데이터베이스에 위임하는 방법 (데이터베이스에 의존적)
          - 주로 MySQL, PostgresSQL, SQL Server, DB2에서 사용합니다.
    */
}
