package com.example.todolist.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainViewModel;
import com.example.todolist.databinding.ObjectTodoBinding;

import java.util.List;

public class MytodoRecyclerViewAdapter extends RecyclerView.Adapter<MytodoRecyclerViewAdapter.ViewHolder> {

    private List<Todo> todoList;
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
        holder.todo_tv_name.setText(todoList.get(position).getUserId());
        holder.todo_tv_todo.setText(todoList.get(position).getTodo());
        holder.todo_cb_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("너 이거 누름");
                    builder.setMessage("삭제할까 말까");
                    builder.setPositiveButton("ㅇㅇ삭제해",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mainViewModel.deleteTodoText(mainViewModel.getDate(), holder.todo_tv_todo.getText().toString());
                                }
                            });
                    builder.setNegativeButton("ㄴㄴ하지마",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();
                }
            }
        });
        holder.todo_bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.deleteTodoText(mainViewModel.getDate(), holder.todo_tv_todo.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final CheckBox todo_cb_checkbox;
        public final TextView todo_tv_name;
        public final TextView todo_tv_todo;
        public final Button todo_bt_delete;
        public final CheckBox todo_cb_checkbox;

        public ViewHolder(ObjectTodoBinding binding) {
            super(binding.getRoot());
//            todo_cb_checkbox = binding.todoCbCheckbox;
            todo_tv_name = binding.todoTvName;
            todo_tv_todo = binding.todoTvTodo;
            todo_bt_delete = binding.todoBtDelete;
            todo_cb_checkbox = binding.todoCbCheckbox;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + todo_tv_todo.getText() + "'";
        }
    }

    public void setTodoList(List<Todo> newList){todoList=newList;
    notifyDataSetChanged();}
}