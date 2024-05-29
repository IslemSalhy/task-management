package com.islem.tasks.dto;
import com.islem.tasks.entity.Project;
import com.islem.tasks.entity.Tasks;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TasksDto {

    private Integer id;

    private String title;

    private String description;

    private String startDate;
    
     private String endDate;
    private Integer idUser ;
    private boolean done ;

    private boolean favorite;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProjectDto project;

    public static Tasks toEntity (TasksDto tasksDto) {
        final Tasks tasks = new Tasks();
        tasks.setId(tasksDto.getId());
        tasks.setTitle(tasksDto.getTitle());
        tasks.setDescription(tasksDto.getDescription());
        tasks.setDone(tasksDto.isDone());
        tasks.setFavorite(tasksDto.isFavorite());
        tasks.setStartDate(ZonedDateTime.now().toString());
        tasks.setProject(ProjectDto.toEntity(tasksDto.getProject()));

        return tasks;
    }
    public static TasksDto fromEntity(Tasks tasks ) {
    	Integer idUser = null ;
    	 if(tasks.getUsers() != null ) {
    		 idUser = tasks.getUsers().getId();
    	 }
        return TasksDto.builder()
                .id(tasks.getId())
                .title(tasks.getTitle())
                .description(tasks.getDescription())
                .startDate(tasks.getStartDate())
                .done(tasks.isDone())
                .idUser(idUser)
                .endDate(tasks.getEndDate())
                .favorite(tasks.isFavorite())
                .build();
    }
}