package com.islem.tasks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.islem.tasks.dto.TasksDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "description", length = 255)
    private String description;
    @ManyToMany@Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> user;
    @OneToMany(mappedBy="project", fetch = FetchType.EAGER)
    private List<Tasks> tasksList;


}


