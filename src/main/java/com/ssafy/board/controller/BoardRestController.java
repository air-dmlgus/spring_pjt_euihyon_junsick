package com.ssafy.board.controller;
import java.net.URI;
import java.util.List;
import com.ssafy.board.dto.Board;
import com.ssafy.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/board")
public class BoardRestController {
 private final BoardService service;
 public BoardRestController(BoardService service) { this.service=service; }
 @GetMapping public ResponseEntity<List<Board>> list() { return ResponseEntity.ok(service.getList()); }
 @GetMapping("/{id}") public ResponseEntity<Board> detail(@PathVariable("id") int id) {
  Board board=service.getBoard(id);
  return board==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(board);
 }
 @PostMapping public ResponseEntity<Board> write(@RequestBody Board board) {
  // Client-supplied identity and counters are never inserted.
  board.setId(null); board.setViewCnt(0); board.setRegDate(null);
  if (!service.writeBoard(board)) throw new IllegalStateException("Insert failed");
  return ResponseEntity.created(URI.create("/api/board/"+board.getId())).body(board);
 }
 @PutMapping("/{id}") public ResponseEntity<Void> modify(@PathVariable("id") int id,@RequestBody Board board) {
  if(service.getBoardForModify(id)==null) return ResponseEntity.notFound().build();
  board.setId(id);
  return service.modifyBoard(board) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> remove(@PathVariable("id") int id) {
  if(service.getBoardForModify(id)==null) return ResponseEntity.notFound().build();
  return service.removeBoard(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
 }
}
