package com.islem.tasks.controller;

import com.islem.tasks.dto.TasksCreateDto;
import com.islem.tasks.dto.TasksDto;
import com.islem.tasks.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @PostMapping(value = "/adminuser/tasks_create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksDto> createTasks(@RequestBody TasksDto tasksDto) {
        return new ResponseEntity<>(tasksService.save(tasksDto), HttpStatus.CREATED);
    }
    @PostMapping(value = "/adminuser/tasksCreate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tasksCreate(@RequestBody TasksCreateDto tasksDto) {
        return new ResponseEntity<>(tasksService.saveTasksCreate(tasksDto), HttpStatus.CREATED);
    }
    @PatchMapping(value = "/adminuser/tasksUpdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tasksUpdate(@RequestBody TasksCreateDto tasksDto) {
        return new ResponseEntity<>(tasksService.tasksUpdate(tasksDto), HttpStatus.CREATED);
    }
    @PatchMapping(value = "/adminuser/tasks_update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksDto> updateTasks(@RequestBody TasksDto tasksDto) {
        return new ResponseEntity<>(tasksService.save(tasksDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/adminuser/tasks_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TasksDto>> getAllTasks() {
        return new ResponseEntity<>(tasksService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/adminuser/tasks_{taskId:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksDto> getTasks(@PathVariable Integer taskId) {
        return new ResponseEntity<>(tasksService.findById(taskId), HttpStatus.OK);
    }

    @GetMapping(value = "/adminuser/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTasksByUser(@PathVariable Integer id) {
        return new ResponseEntity<>(tasksService.getTasksByUser(id), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/adminuser/tasks_delete_/{id}")
    public ResponseEntity deleteTasks(@PathVariable Integer id) {
        tasksService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/adminuser/addTeamMember")
    public ResponseEntity<?> addTeamMember(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {
        tasksService.addTeamMember(taskId, userId);
        return new ResponseEntity<>("Team member added successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/adminuser/assignTaskToUser")
    public ResponseEntity<?> assignTaskToUser(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {
        tasksService.assignTaskToUser(taskId, userId);
        return new ResponseEntity<>("Task assigned to user successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/adminuser/updateTeamMember")
    public ResponseEntity<?> updateTeamMember(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {
        tasksService.updateTeamMember(taskId, userId);
        return new ResponseEntity<>("Team member updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/adminuser/removeTeamMember")
    public ResponseEntity<?> removeTeamMember(@RequestParam("taskId") Integer taskId, @RequestParam("userId") Integer userId) {
        tasksService.removeTeamMember(taskId, userId);
        return new ResponseEntity<>("Team member removed successfully", HttpStatus.OK);
    }
}
