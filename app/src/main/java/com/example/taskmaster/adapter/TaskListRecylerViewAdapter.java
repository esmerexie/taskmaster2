package com.example.taskmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;
import com.example.taskmaster.model.Task;

import java.util.List;

public class TaskListRecylerViewAdapter extends RecyclerView.Adapter {

    List<Task> taskList;

    public TaskListRecylerViewAdapter(List<Task> tasks) {
        this.taskList = tasks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView tasksFragmentName = holder.itemView.findViewById(R.id.taskFragmentTextView);
        TextView tasksFragmentBody = holder.itemView.findViewById(R.id.taskFragmentBodyTextView);
        TextView tasksFragmentState = holder.itemView.findViewById(R.id.taskFragmentStateTextView);
        String taskName = taskList.get(position).getTitle();
        String taskBody = taskList.get(position).getBody();
        String taskState = taskList.get(position).getState();

        tasksFragmentName.setText(position + ". " + taskName);
        tasksFragmentBody.setText(taskBody);
        tasksFragmentState.setText(taskState);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder{

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
