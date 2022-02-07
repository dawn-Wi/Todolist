package com.example.todolist.todo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.MainViewModel;
import com.example.todolist.R;

import java.util.List;

public class TodoFragment extends Fragment {
    MainViewModel mainViewModel;
    ConstraintLayout myFrame;
    List<Todo> todoList;
    EditText todomain_et_text;
    Button todomain_bt_save;
    Button todomain_bt_home;
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
        todomain_bt_home = view.findViewById(R.id.todomain_bt_home);
        myFrame = view.findViewById(R.id.todo_fl);
        todoList = mainViewModel.getTodoList();

        //getName 옵저버, 제대로 가져왔으면 username에 저장
        mainViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sendname) {
                if(sendname!=null){
                    username=sendname;
                }
            }
        });

        //save버튼 클릭시, 날짜, SendTodoText(날짜, 쓰여진 문구)실행, setWriteText(쓰여진 문구) 실행, 텍스트칸 빈칸으로
        todomain_bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todomain_et_text.getText().length()<1){
                    Toast.makeText(getActivity().getApplicationContext(),"try again",Toast.LENGTH_SHORT).show();
                }
                else{
                    mainViewModel.sendTodoText(mainViewModel.getDate(), username, todomain_et_text.getText().toString());
                    mainViewModel.setWriteText(todomain_et_text.getText().toString());
                }
            }
        });

        //sendTodoTextSuccess 옵저버, 제대로 보내졌으면 빈칸으로 만들고 토스트 띄우기
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

        todomain_bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TodoFragment.this).navigate(R.id.action_todoFragment_to_calendarFragment);
            }
        });

        //프레그먼트 매니저,
        FragmentManager fm = getChildFragmentManager();
        Fragment myFrag = TodoListFragment.newInstance(1,todoList);
        //프래그먼트 트랜잭션 초기화
        FragmentTransaction transaction = fm.beginTransaction();
        //전달받은 fragment를 replace
        transaction.replace(myFrame.getId(), myFrag);
        //transaction 마무리
        transaction.commit();
    }
}