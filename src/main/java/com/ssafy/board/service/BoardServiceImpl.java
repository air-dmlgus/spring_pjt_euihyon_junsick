package com.ssafy.board.service;
import java.util.List;
import com.ssafy.board.dao.BoardDao;
import com.ssafy.board.dto.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BoardServiceImpl implements BoardService {
 private final BoardDao dao;
 public BoardServiceImpl(BoardDao dao) { this.dao=dao; }
 public List<Board> getList() { return dao.selectAll(); }
 @Transactional public Board getBoard(int id) { dao.updateViewCnt(id); return dao.selectOne(id); }
 public Board getBoardForModify(int id) { return dao.selectOne(id); }
 @Transactional public boolean writeBoard(Board board) { return dao.insertBoard(board)==1; }
 @Transactional public boolean modifyBoard(Board board) { return dao.updateBoard(board)==1; }
 @Transactional public boolean removeBoard(int id) { return dao.deleteBoard(id)==1; }
}
