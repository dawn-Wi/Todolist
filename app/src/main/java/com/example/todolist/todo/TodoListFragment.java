package com.example.todolist.todo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.todolist.MainViewModel;
import com.example.todolist.R;

import java.util.List;

public class TodoListFragment extends Fragment {
    MainViewModel mainViewModel;
    Button todo_bt_delete;
    String date;
    String text;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public List<Todo> todoList;

    public TodoListFragment(List<Todo> todoList) {this.todoList = todoList;}

    public static TodoListFragment newInstance(int columnCount, List<Todo> todoList) {
        TodoListFragment fragment = new TodoListFragment(todoList);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MytodoRecyclerViewAdapter adapter = new MytodoRecyclerViewAdapter(todoList, new ViewModelProvider(requireActivity()).get(MainViewModel.class));
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

            //isListLoaded 옵저버, true시 어댑터를 통해 setTodoList(리스트 내용)실행, 어댑터한테 recyclerView 리스트 업데이트 다시그려
            mainViewModel.isListLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isChanged) {
                    if(isChanged) {
                        adapter.setTodoList(mainViewModel.getTodoList());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        todo_bt_delete = view.findViewById(R.id.todo_bt_delete);



        mainViewModel.getWriteText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String gettext) {
                text = gettext;
            }
        });




    }
}