package com.islem.tasks.service.impl;

import com.islem.tasks.dto.ProjectDto;
import com.islem.tasks.dto.TasksDto;
import com.islem.tasks.dto.UserDto;
import com.islem.tasks.entity.User;
import com.islem.tasks.exception.InvalidEntityException;
import com.islem.tasks.exception.EntityNotFoundException;
import com.islem.tasks.exception.ErrorCodes;
import com.islem.tasks.entity.Project;
import com.islem.tasks.entity.Tasks;
import com.islem.tasks.repository.ProjectRepository;
import com.islem.tasks.repository.TasksRepository;
import com.islem.tasks.repository.UserRepository;
import com.islem.tasks.service.TasksService;
import com.islem.tasks.validators.TasksValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
@Slf4j

public class TasksServiceImpl implements TasksService {


    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public TasksDto save(TasksDto tasksDto) {
        List<String> errors = TasksValidator.validateTasks(tasksDto);
        if (!errors.isEmpty()) {
            log.error("Tasks is not valid {}", tasksDto);
            throw new InvalidEntityException("Tasks is not valid", ErrorCodes.TASK_NOT_VALID, errors);
        }
        return TasksDto.fromEntity(tasksRepository.save(TasksDto.toEntity(tasksDto)));
    }

    @Override
    public List<TasksDto> findAll() {
        return tasksRepository.findAll().stream()
                .map(TasksDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TasksDto findById(Integer id) {
        if (id == null) {
            log.error("User id is null");
            return null;
        }
        final Integer ProjectId = projectRepository.findProjectByTasksId(id);
        Project Project = new Project();
        Project.setId(ProjectId);

        final Optional<Tasks> Tasks = tasksRepository.findById(id);
        Tasks.ifPresent(value -> value.setProject(Project));

        final TasksDto tasksDto = TasksDto.fromEntity(Tasks.get());
        ProjectDto projectDto = ProjectDto.fromEntity(Project);
        tasksDto.setProject(projectDto);

        return Optional.of(tasksDto).
                orElseThrow(() -> new EntityNotFoundException("No Tasks found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<TasksDto> findByProject(Integer ProjectId) {
        return tasksRepository.findTasksByProjectId(ProjectId).stream()
                .map(TasksDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Tasks id is null");
            return;
        }
        tasksRepository.deleteById(id);
    }
    @Override
    public void addTeamMember(Integer taskId, Integer userId) {
        // Récupérer la tâche correspondant à l'ID
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        // Récupérer le projet associé à la tâche
        Project project = task.getProject();

        // Vérifier si le projet existe
        if (project == null) {
            throw new EntityNotFoundException("Project not found for task with id: " + taskId);
        }

        // Ajouter l'utilisateur à l'équipe du projet
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        project.getUser().add(user);

        // Enregistrer les modifications dans la base de données
        projectRepository.save(project);
    }

    @Override
    public void assignTaskToUser(Integer taskId, Integer userId) {
        // Récupérer la tâche correspondant à l'ID
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        // Récupérer l'utilisateur correspondant à l'ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Vérifier si la liste d'utilisateurs n'est pas vide
        if (!task.getUsers().isEmpty()) {
            // Sélectionnez un utilisateur à partir de la liste, par exemple le premier utilisateur
            User existingUser = task.getUsers().get(0);

            // Assignez cet utilisateur à la tâche
            task.setUsers(Collections.singletonList(existingUser));
        } else {
            // Gérer le cas où la liste d'utilisateurs est vide
            // Vous pouvez choisir d'ajouter l'utilisateur directement à la liste ou de lever une exception
            // Par exemple, ajouter directement l'utilisateur à la liste :
            task.setUsers(Collections.singletonList(user));
            // Ou lever une exception indiquant que la liste d'utilisateurs est vide :
            throw new RuntimeException("La liste d'utilisateurs est vide pour la tâche avec l'ID : " + taskId);
        }

        // Enregistrer les modifications dans la base de données
        tasksRepository.save(task);
    }


    @Override
    public void updateTeamMember(Integer taskId, Integer userId) {
        // Récupérer la tâche correspondant à l'ID
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        // Récupérer l'utilisateur correspondant à l'ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Mettre à jour les détails du membre de l'équipe
        // Par exemple, vous pouvez mettre à jour le nom ou l'email de l'utilisateur
        user.setFirstName("Nouveau nom");
        user.setLastName("Nouveau prénom");
        user.setEmail("nouveau@email.com");


        // Enregistrer les modifications dans la base de données
        userRepository.save(user);
    }

    @Override
    public void removeTeamMember(Integer taskId, Integer userId) {
        // Récupérer la tâche correspondant à l'ID
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        // Récupérer l'utilisateur correspondant à l'ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Supprimer l'utilisateur de la liste des membres de l'équipe
        task.getUser().remove(user);

        // Enregistrer les modifications dans la base de données
        tasksRepository.save(task);
    }

}
