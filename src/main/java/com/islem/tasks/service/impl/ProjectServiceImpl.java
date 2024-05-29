package com.islem.tasks.service.impl;

import com.islem.tasks.dto.ProjectDto;
import com.islem.tasks.dto.TasksDto;
import com.islem.tasks.exception.InvalidEntityException;
import com.islem.tasks.exception.EntityNotFoundException;
import com.islem.tasks.exception.ErrorCodes;
import com.islem.tasks.repository.ProjectRepository;
import com.islem.tasks.repository.TasksRepository;
import com.islem.tasks.repository.UserRepository;
import com.islem.tasks.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.islem.tasks.validators.ProjectValidator;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;
import com.islem.tasks.entity.*;
@Service
@Slf4j



public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
   
    @Autowired
    private  TasksRepository tasksRepository;
    
    @Autowired
    private UserRepository userRepository;
    @Override
    public ProjectDto save(ProjectDto project) {
        List<String> errors = ProjectValidator.validatePoject(project);
        if (!errors.isEmpty()) {
            log.error("Project is not valid {}", project);
            throw new InvalidEntityException("Project is not valid", ErrorCodes.PROJECT_NOT_VALID, errors);
        }
        Project savedProject = projectRepository.save(ProjectDto.toEntity(project));
        ProjectDto projetDto = ProjectDto.fromEntity(savedProject);
        if(project.getTasksList().size()> 0) {
        	 for (TasksDto taskDto : project.getTasksList()) {
        		Optional<User> selectedUser =  userRepository.findById(taskDto.getIdUser()) ;
        		 Tasks task = new Tasks();
        		 task.setDescription(taskDto.getDescription());
        		 task.setDone(false);
        		 task.setTitle(taskDto.getTitle());
        		 task.setEndDate(savedProject.getEndDate());
        		 task.setFavorite(false);
        		 task.setProject(savedProject);
        		 task.setUsers(selectedUser.get());
        		 tasksRepository.save(task);
        	    }
        }
        return projetDto ;
    }
    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto findById(Integer id) {
        if (id == null) {
            log.error("Project id is null");
            return null;
        }
        return projectRepository.findById(id).map(ProjectDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("No Project found with ID = " + id,ErrorCodes.USER_NOT_FOUND));
    }
    @Override
    public List<ProjectDto> findAllByUserId(Integer userId) {
        return projectRepository.findProjectByUserId(userId).stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Project id is null");
            return;
        }
        List<Tasks>  listtask = tasksRepository.findTasksByProjectId(id);
        if(listtask.size()>0) {
            tasksRepository.deleteAll(listtask);

        }

        projectRepository.deleteById(id);
        
    }

    @Override
    public List<ProjectDto> getAllTasksByProjectsForToday(Integer userId) {
        return projectRepository.getAllTasksByProjectsForToday(ZonedDateTime.now().withHour(0).withMinute(0),
                        ZonedDateTime.now().withHour(23).withMinute(59), userId)
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }
    //start

    //end
    public List<Project>findProjectsEndingToday(){
        return projectRepository.findProjectsEndingToday();
    }
	@Override
	public Object UpdateProject(ProjectDto projectDto) {
		// TODO Auto-generated method stub
		Optional<Project> findProject = projectRepository.findById(projectDto.getId());
		if(findProject.isPresent()) {
			findProject.get().setDescription(projectDto.getDescription());
			findProject.get().setEndDate(projectDto.getEndDate());
			findProject.get().setName(projectDto.getName());
			projectRepository.save(findProject.get());
		}
		return findProject;
	}

}