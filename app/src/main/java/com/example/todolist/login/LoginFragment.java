package com.example.todolist.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.FirebaseDataSource;
import com.example.todolist.MainViewModel;
import com.example.todolist.R;
import com.example.todolist.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    MainViewModel mainViewModel;
    EditText login_et_email;
    EditText login_et_password;
    Button login_bt_login;
    Button login_bt_signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        FirebaseDataSource ds = new FirebaseDataSource();
        UserRepository.getInstance().setDataSource(ds);
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

        //getDoingWork 옵저버, 돌아가는 중(true)이면 안써지게, 안돌아가면(false) 써지게
        mainViewModel.getDoingWork().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isWorking) {
                if(isWorking==true){
                    login_et_email.setEnabled(false);
                    login_et_password.setEnabled(false);
                    login_bt_login.setEnabled(false);
                    login_bt_signup.setEnabled(false);
                }
                else{
                    login_et_email.setEnabled(true);
                    login_et_password.setEnabled(true);
                    login_bt_login.setEnabled(true);
                    login_bt_signup.setEnabled(true);
                    login_et_email.setText(null);
                    login_et_password.setText(null);
                }
            }
        });

        //isLoggedIn 옵저버,로그인이 true이면 화면이동, 유저ID를 MainViewModel의 setName에 저장
        mainViewModel.isLoggedIn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoggedIn) {
                if(isLoggedIn==true){
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_calendarFragment);
                    mainViewModel.setName(login_et_email.getText().toString());
                }
            }
        });

        //login_bt_login버튼을 누르면
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
                else{
                    //ID, Password 다 형식대로 쓰면 tryLogin 시작해봐, ID랑 Password넘겨줘서
                    mainViewModel.tryLogin(login_et_email.getText().toString(),login_et_password.getText().toString());
                }
            }
        });

        //login_bt_signup버튼 누르면
        login_bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입창으로 넘어가
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });

    }
}