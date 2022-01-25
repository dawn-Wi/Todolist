package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TodoFragment extends Fragment {
    MainViewModel mainViewModel;
    Button todo_bt_save;

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
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todo,container,false);
//        initUI(rootView);
//        return rootView;
//    }
//    private void initUI(ViewGroup rootView){
//
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        todo_bt_save = view.findViewById(R.id.todo_bt_save);

        todo_bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}