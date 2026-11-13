package com.ssafy.board.service;
import java.util.List;
import com.ssafy.board.dto.Board;
public interface BoardService {
 List<Board> getList(); Board getBoard(int id); Board getBoardForModify(int id);
 boolean writeBoard(Board board); boolean modifyBoard(Board board); boolean removeBoard(int id);
}
