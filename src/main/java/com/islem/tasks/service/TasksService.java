package com.islem.tasks.service;

import com.islem.tasks.dto.TasksCreateDto;
import com.islem.tasks.dto.TasksDto;
import com.islem.tasks.entity.Tasks;

import java.util.List;

public interface TasksService {

    TasksDto save(TasksDto tasksDto);

    List<TasksDto> findAll();

    TasksDto findById(Integer id);

    List<TasksDto> findByProject(Integer projectId);

    void delete(Integer id);

    void assignTaskToUser(Integer taskId, Integer userId);

    void addTeamMember(Integer taskId, Integer userId);

    void updateTeamMember(Integer taskId, Integer userId);

    void removeTeamMember(Integer taskId, Integer userId);

	Object saveTasksCreate(TasksCreateDto tasksDto);

	Object tasksUpdate(TasksCreateDto tasksDto);

	List<Tasks> getTasksByUser(Integer id);

}
