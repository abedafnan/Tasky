package com.example.android.tasky;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Afnan A. A. Abed on 9/4/2018.
 */

public class Task {
    private String taskName;
    private int taskPriority;
    private long taskTime;

    public Task(String taskName, int taskPriority, long taskTime) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskTime = taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }

    @Override
    public boolean equals(Object obj) {
        return (taskName.equals(((Task) obj).getTaskName()) && taskPriority == ((Task) obj).getTaskPriority())
                || taskPriority == ((Task) obj).getTaskPriority();
    }
}

