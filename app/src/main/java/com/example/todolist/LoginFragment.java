package com.example.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    EditText login_et_email;
    EditText login_et_password;
    Button login_bt_login;
    Button login_bt_signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        login_et_email = view.findViewById(R.id.login_et_email);
        login_et_password= view.findViewById(R.id.login_et_password);
        login_bt_login = view.findViewById(R.id.login_bt_login);
        login_bt_signup = view.findViewById(R.id.login_bt_signup);

        login_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //처음에 로그인 시 ID가 이메일 형식인지 확인 하는 작업
                boolean returnValue = false;
                String regex =  "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
                Pattern p =Pattern.compile(regex);
                Matcher m =p.matcher(login_et_email.getText().toString());
                if(m.matches()){
                    returnValue = true;
                }
                if(returnValue == false){
                    //AlertDialog로 아이디 실패시 창 뜨기
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("LOGIN 실패");
                    builder.setMessage("ID를 다시 확인하세요");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    Toast.makeText(getActivity().getApplicationContext(),"Yes",Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    Toast.makeText(getActivity().getApplicationContext(),"No",Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.show();
                }
                //AlertDialog로 비밀번호 4자리 이하 실패시 창 뜨기
                else if(login_et_password.getText().toString().trim().length()<4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("LOGIN 싪패");
                    builder.setMessage("비밀번호를 4자리 수 이상 입력해주세요");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    Toast.makeText(getActivity().getApplicationContext(),"Yes",Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    Toast.makeText(getActivity().getApplicationContext(),"No",Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.show();
                }
            }
        });

        login_bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

    }
}