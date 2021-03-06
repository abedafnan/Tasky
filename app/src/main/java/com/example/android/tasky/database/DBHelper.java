package com.example.android.tasky.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.tasky.database.DBContract.TaskEntry;

/**
 * Created by Afnan A. A. Abed on 9/2/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TasksDatabase";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private final String CREATE_TASKS_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
            + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TaskEntry.NAME_COLUMN + " TEXT NOT NULL, "
            + TaskEntry.PRIORITY_COLUMN + " INTEGER NOT NULL DEFAULT 1, "
            + TaskEntry.TIME_COLUMN + " INTEGER NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TaskEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }
}
