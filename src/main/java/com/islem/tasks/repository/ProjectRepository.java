package com.islem.tasks.repository;

import com.islem.tasks.dto.ProjectDto;
import com.islem.tasks.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findProjectByUserId(Integer userId);

    @Query("select t.project.id from Tasks t where t.id = :tasksId")
    Integer findProjectByTasksId(Integer tasksId);

    @Query("select distinct p from Project p join p.tasksList t where t.startDate >= :startDate and t.startDate <= :endDate and :userId member of p.user")
    List<Project> getAllTasksByProjectsForToday(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("userId") Integer userId);

    @Query("SELECT p FROM Project p WHERE FUNCTION('DATE_FORMAT', p.endDate, '%Y-%m-%d') = FUNCTION('DATE_FORMAT', CURRENT_DATE(), '%Y-%m-%d')")
    List<Project> findProjectsEndingToday();


}
