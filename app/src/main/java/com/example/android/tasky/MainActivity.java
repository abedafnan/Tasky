package com.example.android.tasky;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> mTasks;
    RecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAddDialog();
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);

        mTasks = new ArrayList<>();
        mTasks.add(new Task("Task 1", 1, 0));
        mTasks.add(new Task("Task 2", 2, 0));
        mTasks.add(new Task("Task 3", 3, 0));
        mTasks.add(new Task("Task 4", 4, 0));
        mTasks.add(new Task("Task 5", 5, 0));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(mTasks);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addOnItemTouchListener();
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

    public LinearLayout createLayout() {
        // Create and style the layout for the dialogs
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(60, 30, 60, 0);

        // Create and style the task input field
        EditText taskInput = new EditText(this);
        taskInput.setInputType(InputType.TYPE_CLASS_TEXT);
        taskInput.setHint("Enter new task");

        // Create and style the priority input field
        EditText priorityInput = new EditText(this);
        priorityInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        priorityInput.setHint("Enter task priority");

        // Adding the views to the layout
        layout.addView(taskInput, layoutParams);
        layout.addView(priorityInput, layoutParams);

        return layout;
    }

    public void viewAddDialog() {
        // Create the dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Add New Task");
        dialog.setCancelable(true);
        dialog.setView(createLayout());

        // Set up the buttons
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

    public void viewUpdateDialog() {
        // Create the dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Update Task");
        dialog.setCancelable(true);
        dialog.setView(createLayout());

        // Set up the buttons
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

    public void viewDeleteDialog() {
        // Create the dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete Task");
        dialog.setCancelable(true);

        // Create and style the layout of the dialog
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(60, 30, 60, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create and style the to be deleted task number input field
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Enter task number");

        //  Adding the views to the layout
        layout.addView(editText, layoutParams);
        dialog.setView(layout);

        // Set up the buttons
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "task number not entered!", Toast.LENGTH_SHORT).show();
                } else {
                    confirmDeletion();
                }
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

    public void confirmDeletion() {
        // Create the dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Delete this task ?");
        dialog.setCancelable(true);

        // Set up the buttons
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

}
