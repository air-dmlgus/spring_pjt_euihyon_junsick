package com.ssafy.board.dao;
import java.util.List;
import com.ssafy.board.dto.Board;
public interface BoardDao {
 List<Board> selectAll();
 Board selectOne(int id);
 int updateViewCnt(int id);
 int insertBoard(Board board);
 int updateBoard(Board board);
 int deleteBoard(int id);
}
