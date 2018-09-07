package com.example.android.tasky.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.android.tasky.Task;
import java.util.ArrayList;
import com.example.android.tasky.database.DBContract.TaskEntry;

/**
 * Created by Afnan A. A. Abed on 9/2/2018.
 */

public class DBOperations {

    private DBHelper dbHelper;

    public DBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long addTask(Task task) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.NAME_COLUMN, task.getTaskName());
        values.put(TaskEntry.PRIORITY_COLUMN, task.getTaskPriority());
        values.put(TaskEntry.TIME_COLUMN, task.getTaskTime());

        return database.insert(TaskEntry.TABLE_NAME, null, values);
    }

    public int deleteTask(int taskNumber) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String whereClause = TaskEntry.PRIORITY_COLUMN + " = ?";
        String[] whereArgs = new String[]{String.valueOf(taskNumber)};

        return database.delete(TaskEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public int updateTask(Task oldTask, Task newTask) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.NAME_COLUMN, newTask.getTaskName());
        values.put(TaskEntry.PRIORITY_COLUMN, newTask.getTaskPriority());
        values.put(TaskEntry.TIME_COLUMN, newTask.getTaskTime());

        String whereClause = TaskEntry.PRIORITY_COLUMN + " = ?";
        String[] whereArgs = new String[]{String.valueOf(oldTask.getTaskPriority())};

        return database.update(TaskEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public ArrayList<Task> readAllTasks() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ArrayList<Task> tasksArrayList = new ArrayList<>();

        Cursor cursor = database.query(TaskEntry.TABLE_NAME, null, null,
                null, null, null, TaskEntry.PRIORITY_COLUMN + " ASC");

        while (cursor.moveToNext()) {
            String taskName = cursor.getString(cursor.getColumnIndex(TaskEntry.NAME_COLUMN));
            int taskPriority = cursor.getInt(cursor.getColumnIndex(TaskEntry.PRIORITY_COLUMN));
            long taskTime = cursor.getLong(cursor.getColumnIndex(TaskEntry.TIME_COLUMN));

            Task task = new Task(taskName, taskPriority, taskTime);
            tasksArrayList.add(task);
        }

        cursor.close();
        return tasksArrayList;
    }
}
