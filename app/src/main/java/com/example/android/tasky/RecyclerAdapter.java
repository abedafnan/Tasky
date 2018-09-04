package com.example.android.tasky;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Afnan A. A. Abed on 9/4/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Task> mTasks;
    private onItemClickListener mListener;

    public RecyclerAdapter(ArrayList<Task> tasks, onItemClickListener listener) {
        mTasks = tasks;
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskPriority;

        public ViewHolder(View taskView) {
            super(taskView);
            taskName = taskView.findViewById(R.id.task_input);
            taskPriority = taskView.findViewById(R.id.priority_input);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, viewHolder.getPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.taskName.setText(mTasks.get(position).getTaskName());
        holder.taskPriority.setText("" + mTasks.get(position).getTaskPriority());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
}
