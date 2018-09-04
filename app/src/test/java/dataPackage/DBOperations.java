package dataPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dataPackage.DBContract.TaskEntry;
import com.example.android.tasky.Task;

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
}
