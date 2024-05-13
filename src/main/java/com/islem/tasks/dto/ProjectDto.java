package com.islem.tasks.dto;


import com.islem.tasks.entity.Project;
import com.islem.tasks.entity.Tasks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProjectDto {

    private Integer id;

    private String name;

    private String description;

    private List<UserDto> user;

    private List<TasksDto> tasksList;

    public static Project toEntity(ProjectDto projectDto) {
        Project project = new Project();

        project.setUser(projectDto.getUser().stream().map(UserDto::toEntity).collect(Collectors.toList()));
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());


        return project;
    }

    public static ProjectDto fromEntity(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .tasksList(
                        project.getTasksList() != null ? project.getTasksList().stream().map(TasksDto::fromEntity).collect(Collectors.toList()) : null
                )
                .build();
    }
}
