package com.example.android.tasky;

/**
 * Created by Afnan A. A. Abed on 9/4/2018.
 */

public class Task {
    private String taskName;
    private String taskPriority;

    public Task(String taskName, String taskPriority) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }
}
