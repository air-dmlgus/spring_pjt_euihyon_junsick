package com.ssafy.board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
@MapperScan("com.ssafy.board.dao")
public class BoardApplication {
 public static void main(String[] args) { SpringApplication.run(BoardApplication.class,args); }
}
