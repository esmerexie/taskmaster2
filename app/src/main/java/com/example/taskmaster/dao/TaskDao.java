package com.example.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmaster.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertTask(Task task);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task WHERE type = :type")
    public List<Task> findAllByType(Task.TaskTypeEnum type);

    @Query("SELECT * FROM Task ORDER BY title ASC")
    public List<Task> findAllSortedByTask();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findByAnId(long id);
}
