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

public class SignupFragment extends Fragment {
    MainViewModel mainViewModel;
    EditText signup_et_name;
    EditText signup_et_eamil;
    EditText signup_et_password;
    Button signup_bt_go;

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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        signup_et_name = view.findViewById(R.id.signup_et_name);
        signup_et_eamil = view.findViewById(R.id.signup_et_email);
        signup_et_password = view.findViewById(R.id.signup_et_password);
        signup_bt_go = view.findViewById(R.id.signup_bt_go);

        //다쓰고 signup_bt_go버튼 누르면 ID랑 Password 형식 맞는지 확인해봐
        signup_bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean returnValue = false;
                String regex =  "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
                Pattern p =Pattern.compile(regex);
                Matcher m =p.matcher(signup_et_eamil.getText().toString());
                if(m.matches()){
                    returnValue = true;
                }
                if(returnValue == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("SIGNUP 실패");
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
                else if(signup_et_password.getText().toString().trim().length()<4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("SIGNUP 싪패");
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
                else if(signup_et_name.getText().toString().trim().length()<1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("SIGNUP 싪패");
                    builder.setMessage("이름을 두글자 이상 입력해주세요");
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
                    //다 완벽하면 tryRegister에 ID랑 Password랑 Name 넘겨서 시도해봐
                    mainViewModel.tryRegister(signup_et_eamil.getText().toString(),signup_et_password.getText().toString(),signup_et_name.getText().toString());
                }
            }
        });

        //registerSuccess옵저버, 성공하면 로그인 화면으로, 실패하면 빈칸으로 만들어
        mainViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean registersuccessing) {
                if(registersuccessing == true){
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                    mainViewModel.setRegisterSuccess(false);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                    signup_et_eamil.setText(null);
                    signup_et_password.setText(null);
                    signup_et_name.setText(null);
                }
            }
        });
    }
}