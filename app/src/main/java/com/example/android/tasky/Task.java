package com.example.android.tasky;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

/**
 * Created by Afnan A. A. Abed on 9/4/2018.
 */

public class Task {
    private String taskName;
    private int taskPriority;
    private int taskTime;

    public Task(String taskName, int taskPriority, int taskTime) {
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

    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }
}
