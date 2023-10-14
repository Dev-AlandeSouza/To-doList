package br.com.devAlanSouza.TodoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.devAlanSouza.TodoList.utils.Utils;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @RequestMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, @RequestHeader("idUser") UUID idUser) {
        taskModel.setIdUser(idUser);

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A Data de inicío/Termino deve ser maior que a Data atual!");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor do que a de termino!");
        }

        TaskModel task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(@RequestHeader("idUser") UUID idUser) {
        List<TaskModel> tasks = this.taskRepository.findByIdUser(idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, @RequestHeader("idUser") UUID idUser) {

        TaskModel task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada...");
        }

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário sem permissão para alterar essa tarefa!");
        }

        Utils.copyNonNullProperties(taskModel, task);

        TaskModel taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
