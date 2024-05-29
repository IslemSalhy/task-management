package com.islem.tasks.dto;
import com.islem.tasks.entity.Project;
import com.islem.tasks.entity.Tasks;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class TasksCreateDto {

	 private Integer id;
    private Integer idProject;

    private String title;

    private String description;

    private String startDate;
    
    private String endDate;

    private Integer idUser ;
    
    private boolean favorite;

}