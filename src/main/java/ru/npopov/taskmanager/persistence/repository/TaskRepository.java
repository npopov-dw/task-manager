package ru.npopov.taskmanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.npopov.taskmanager.persistence.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
