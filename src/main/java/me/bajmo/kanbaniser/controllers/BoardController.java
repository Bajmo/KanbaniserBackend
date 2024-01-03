package me.bajmo.kanbaniser.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.bajmo.kanbaniser.entities.Board;
import me.bajmo.kanbaniser.exceptions.BoardNotFoundException;
import me.bajmo.kanbaniser.exceptions.TaskNotFoundException;
import me.bajmo.kanbaniser.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/boards")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody Board board) {
        boardService.saveBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        try {
            List<Board> boards = boardService.getBoardRepository().findAll();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        try {
            Board board = boardService.getBoardRepository().findById(id)
                    .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found!"));
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable Long id, @RequestBody Board updatedBoard) {
        try {
            boardService.updateBoard(id, updatedBoard);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<Void> addTaskToBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        try {
            boardService.addTaskToBoard(taskId, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        try {
            boardService.removeTaskFromBoard(taskId, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException | BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}