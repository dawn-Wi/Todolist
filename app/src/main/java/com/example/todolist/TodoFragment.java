package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class TodoFragment extends Fragment {
    MainViewModel mainViewModel;
    ConstraintLayout myFrame;
    List<Todo> todoList;
    EditText todomain_et_text;
    Button todomain_bt_save;
    String calendardate;
    String username;

    public TodoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        todomain_bt_save = view.findViewById(R.id.todomain_bt_save);
        todomain_et_text = view.findViewById(R.id.todomain_et_text);
        myFrame = view.findViewById(R.id.todo_fl);
        todoList = mainViewModel.getTodoList();

        mainViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String choosedate) {
                if(choosedate !=null){
                    calendardate=choosedate;
                }
            }
        });
        mainViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sendname) {
                if(sendname!=null){
                    username=sendname;
                }
            }
        });

        todomain_bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sendTodoText(calendardate, username, todomain_et_text.getText().toString());
                todomain_et_text.setText(null);
            }
        });

        //뭔가 안돌아가는중인듯?
        mainViewModel.sendTodoTextSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sendtodotextsuccessing) {
                if(sendtodotextsuccessing==true){
                    //보내기 성공시
                    todomain_et_text.setText(null);
                    Toast.makeText(getActivity().getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = TodoListFragment.newInstance(1,todoList);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(myFrame.getId(), myFrag);
        transaction.commit();
    }
}