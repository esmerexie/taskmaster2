package com.example.taskmaster.model;

import androidx.annotation.NonNull;

import java.util.Date;


public class Task {

    public Long id;
    private String title;
    private String body;
    private String state;
    private TaskTypeEnum type;
    private java.util.Date dateCreated;

    public Task() {
    }

    public Task(String taskName, String taskBody, String taskState, TaskTypeEnum taskTypeEnum, Date newDate) {
        this.title = taskName;
        this.body = taskBody;
        this.state = taskState;
        this.type = taskTypeEnum;
        this.dateCreated = newDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TaskTypeEnum getType() {
        return type;
    }

    public void setType(TaskTypeEnum type) {
        this.type = type;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public enum TaskTypeEnum {
        GYM("Gym"),
        EAT("Eat"),
        CODE("Code");

        private final String taskType;

        TaskTypeEnum(String _taskType) {
            this.taskType = _taskType;
        }

        public String getTaskType() {
            return taskType;
        }

        public static TaskTypeEnum fromString(String possibleTaskType) {
            for (TaskTypeEnum type : TaskTypeEnum.values()) {
                if (type.taskType.equals(possibleTaskType)) {
                    return type;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            if (taskType == null) {
                return "";
            }
            return taskType;
        }
    }
}
