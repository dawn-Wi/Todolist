package com.example.todolist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.ObjectTodoBinding;

import java.util.List;

public class MytodoRecyclerViewAdapter extends RecyclerView.Adapter<MytodoRecyclerViewAdapter.ViewHolder> {

    private final List<Todo> todoList;
    private MainViewModel mainViewModel;

    public MytodoRecyclerViewAdapter(List<Todo> items, MainViewModel mvm) {
        todoList = items;
        mainViewModel = mvm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ObjectTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.todo_cb_checkbox.setText(todoList.get(position).get_id());
        holder.todo_tv_todo.setText(todoList.get(position).getTodo());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final CheckBox todo_cb_checkbox;
        public final TextView todo_tv_todo;

        public ViewHolder(ObjectTodoBinding binding) {
            super(binding.getRoot());
//            todo_cb_checkbox = binding.todoCbCheckbox;
            todo_tv_todo = binding.todoTvTodo;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + todo_tv_todo.getText() + "'";
        }
    }
}