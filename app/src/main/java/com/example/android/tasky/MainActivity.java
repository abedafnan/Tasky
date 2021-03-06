package com.example.android.tasky;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.tasky.database.DBOperations;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBOperations mDBOperations;
    private RecyclerAdapter mAdapter;
    private ArrayList<Task> mTasks;
    private EditText mTaskInput;
    private EditText mPriorityInput;
    private EditText mTaskUpdate;
    private EditText mPriorityUpdate;
    private EditText mDeleteEditText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Click listener of the floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAddDialog();
            }
        });

        // Read data from the database
        mDBOperations = new DBOperations(this);
        mTasks = mDBOperations.readAllTasks();

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // RecyclerView items' click listener
        mAdapter = new RecyclerAdapter(mTasks, new RecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                viewUpdateDialog(position);
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            viewDeleteDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Create and style the add dialog
    public void viewAddDialog() {
        // Create and style the layout of the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(60, 30, 60, 0);

        // Create and style the text fields
        mTaskInput = new EditText(this);
        mTaskInput.setInputType(InputType.TYPE_CLASS_TEXT);
        mTaskInput.setHint("Enter new task");

        mPriorityInput = new EditText(this);
        mPriorityInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mPriorityInput.setHint("Enter task priority");

        // Add both text fields to the dialog layout
        layout.addView(mTaskInput, layoutParams);
        layout.addView(mPriorityInput, layoutParams);

        // Create the add dialog
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Add Task")
                .setCancelable(true)
                .setView(layout)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                // Create the positive button of the dialog and attach button click listener
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackground(getResources().getDrawable(R.drawable.button_selector3));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkInputValidation(mTaskInput, mPriorityInput)) {

                            if (mTasks.contains(new Task(mTaskInput.getText().toString()
                                    , Integer.parseInt(mPriorityInput.getText().toString()), 0))) {

                                Toast.makeText(MainActivity.this,
                                        "either task name or task priority number already exist", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            addTask();
                            dialog.dismiss();
                        }
                    }
                });

                // Create the negative button of the dialog and attach button click listener
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        dialog.show();
    }

    // Get data form text fields and add it to the database
    public void addTask() {
        String taskName = mTaskInput.getText().toString().trim();
        int taskPriority = Integer.parseInt(mPriorityInput.getText().toString().trim());
        long taskTime =  new Date(System.currentTimeMillis()).getTime();

        Task newTask = new Task(taskName, taskPriority, taskTime);
        long rowId = mDBOperations.addTask(newTask);

        if (rowId > 0) {
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error while adding the task", Toast.LENGTH_SHORT).show();
        }

        mTasks = mDBOperations.readAllTasks();
        mAdapter.addTasks(mTasks);
    }

    // Check the validation of text fields' input
    private boolean checkInputValidation(EditText firstField, EditText secondField) {
        if (!firstField.getText().toString().trim().equals("")) {

            if (!(secondField.getText().toString().trim().equals("") ||
                    Integer.parseInt(secondField.getText().toString().trim()) <= 0)) {
                return true;

            } else {
                Toast.makeText(MainActivity.this,
                        "invalid task priority number!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(MainActivity.this,
                    "task not entered!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // Create and style the update dialog
    public void viewUpdateDialog(final int position) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(60, 30, 60, 0);

        mTaskUpdate = new EditText(this);
        mTaskUpdate.setInputType(InputType.TYPE_CLASS_TEXT);
        mTaskUpdate.setHint("Enter new task");
        mTaskUpdate.setText(mTasks.get(position).getTaskName());

        mPriorityUpdate = new EditText(this);
        mPriorityUpdate.setInputType(InputType.TYPE_CLASS_NUMBER);
        mPriorityUpdate.setHint("Enter task priority");
//        mPriorityUpdate.setText("" + mTasks.get(position).getTaskPriority());

        layout.addView(mTaskUpdate, layoutParams);
        layout.addView(mPriorityUpdate, layoutParams);

        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Update Task")
                .setCancelable(true)
                .setView(layout)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Update", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackground(getResources().getDrawable(R.drawable.button_selector3));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkInputValidation(mTaskUpdate, mPriorityUpdate)) {

                            if (mTasks.contains(new Task(mTaskUpdate.getText().toString()
                                    ,Integer.parseInt(mPriorityUpdate.getText().toString()),0))){

                                Toast.makeText(MainActivity.this, "You already have a task with priority "
                                                + mPriorityUpdate.getText().toString(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            updateTask(position);
                            dialog.dismiss();
                        }
                    }
                });

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        dialog.show();
    }

    // Get data form the text fields and update database
    public void updateTask(int position) {
        String newTask = mTaskUpdate.getText().toString().trim();
        int newPriority = Integer.parseInt(mPriorityUpdate.getText().toString().trim());
        long taskTime = new Date(System.currentTimeMillis()).getTime();

        Task updatedTask = new Task(newTask, newPriority, taskTime);
        Task oldTask = mTasks.get(position);
        int updateId = mDBOperations.updateTask(oldTask, updatedTask);

        mTasks = mDBOperations.readAllTasks();
        mAdapter.addTasks(mTasks);

        if (updateId > 0) {
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error while updating the task", Toast.LENGTH_SHORT).show();
        }
    }

    // Create and style the delete dialog
    public void viewDeleteDialog() {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(60, 30, 60, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        mDeleteEditText = new EditText(this);
        mDeleteEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mDeleteEditText.setHint("Enter task priority number");

        layout.addView(mDeleteEditText, layoutParams);

        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Task")
                .setCancelable(true)
                .setView(layout)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackground(getResources().getDrawable(R.drawable.button_selector1));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mDeleteEditText.getText().toString().trim().equals("")) {
                            Toast.makeText(MainActivity.this,
                                    "task number not entered!", Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(mDeleteEditText.getText().toString().trim()) <= 0) {
                            Toast.makeText(MainActivity.this,
                                    "invalid task number!", Toast.LENGTH_SHORT).show();

                        } else {
                            confirmDeletion();
                            dialog.dismiss();
                        }
                    }
                });

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        dialog.show();
    }

    // Create the deletion confirmation dialog
    public void confirmDeletion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Delete this task ?");
        dialog.setCancelable(true);

        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTask();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.create().show();
    }

    // Delete task from the database
    public void deleteTask() {
        int taskNumber = Integer.parseInt(mDeleteEditText.getText().toString().trim());
        int deletionId = mDBOperations.deleteTask(taskNumber);

        mTasks = mDBOperations.readAllTasks();
        mAdapter.addTasks(mTasks);

        if (deletionId > 0) {
            Toast.makeText(this, "Task was deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This task doesn't exist!", Toast.LENGTH_SHORT).show();
        }
    }
}
