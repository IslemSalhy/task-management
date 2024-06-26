package com.islem.tasks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tasks implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private boolean done;
    private String startDate;
    private String endDate;
    private boolean favorite;

    @ManyToOne
    private User users; // Relation Many-to-Many avec User

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

  



}
