package me.bajmo.kanbaniser.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.bajmo.kanbaniser.entities.Board;
import me.bajmo.kanbaniser.entities.Task;
import me.bajmo.kanbaniser.exceptions.BoardNotFoundException;
import me.bajmo.kanbaniser.exceptions.TaskNotFoundException;
import me.bajmo.kanbaniser.repositories.BoardRepository;
import me.bajmo.kanbaniser.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BoardService {

    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public void deleteBoard(Long id) throws BoardNotFoundException {
        boardRepository.deleteById(id);
    }

    public void updateBoard(Long id, Board updatedBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found!"));
        board.setTitle(updatedBoard.getTitle());
        board.setDescription(updatedBoard.getDescription());
        board.setTasks(updatedBoard.getTasks());
        boardRepository.save(board);
    }

    @Transactional
    public void addTaskToBoard(Long taskId, Long boardId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found!"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + boardId + " not found!"));
        board.getTasks().add(task);
        boardRepository.save(board);
    }

    @Transactional
    public void removeTaskFromBoard(Long taskId, Long boardId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found!"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + boardId + " not found!"));
        board.getTasks().remove(task);
        boardRepository.save(board);
    }

    public JpaRepository<Board, Long> getBoardRepository() {
        return this.boardRepository;
    }

}
