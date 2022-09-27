package com.example.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.taskmaster.dao.TaskDao;
import com.example.taskmaster.model.Task;

@TypeConverters({TaskDatabaseConverters.class})
@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
