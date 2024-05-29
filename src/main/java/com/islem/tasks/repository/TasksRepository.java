package com.islem.tasks.repository;

import com.islem.tasks.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Tasks, Integer>{
    List<Tasks> findTasksByProjectId(Integer projectId);

	List<Tasks> findTasksByUsers_id(Integer id);
}
