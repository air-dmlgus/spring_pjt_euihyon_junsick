package com.ssafy.board.dto;
public class Board {
 private Integer id;
 public Integer getId() { return id; }
 public void setId(Integer id) { this.id=id; }
 private String title;
 public String getTitle() { return title; }
 public void setTitle(String title) { this.title=title; }
 private String writer;
 public String getWriter() { return writer; }
 public void setWriter(String writer) { this.writer=writer; }
 private String content;
 public String getContent() { return content; }
 public void setContent(String content) { this.content=content; }
 private int viewCnt;
 public int getViewCnt() { return viewCnt; }
 public void setViewCnt(int viewCnt) { this.viewCnt=viewCnt; }
 private java.time.LocalDateTime regDate;
 public java.time.LocalDateTime getRegDate() { return regDate; }
 public void setRegDate(java.time.LocalDateTime regDate) { this.regDate=regDate; }
}
