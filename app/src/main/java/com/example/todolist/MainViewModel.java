package com.example.todolist;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> loggedIn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> sendTodoTextSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loggedname = new MutableLiveData<>();
    private MutableLiveData<String> choosedate = new MutableLiveData<>();
    private MutableLiveData<Boolean> receivedate = new MutableLiveData<>(false);
    private List<Todo> todoList;

    public void tryLogin(String id,String password){
        doingWork.setValue(true);
        userRepository.tryLogin(id,password,result -> {
            if(result.equals("Success")){
                loggedIn.setValue(true);
            }
            doingWork.setValue(false);
        });
    }

    public void tryRegister(String id, String password, String name){
        doingWork.setValue(true);
        userRepository.tryRegister(id, password, name, result ->{
           if(result.equals("Success")){
                registerSuccess.postValue(true);
           }
           else{
               registerSuccess.postValue(false);
           }
           doingWork.setValue(false);
        });
    }

    public void sendTodoText(String date,String name, String text){
        userRepository.sendTodoText(date,name,text, result->{
            if(result.equals("Success")){
                sendTodoTextSuccess.postValue(true);
                Log.d("DEBUG", "sendTodoText Success");
            }
            else{
                sendTodoTextSuccess.postValue(false);
                Log.d("DEBUG", "sendTodoText Failed");
            }
        });
    }

    public void getTodoText(String date){
        userRepository.getTodoText(date, result->{
            if(result instanceof Result.Success){
                receivedate.setValue(true);
                todoList = ((Result.Success<List<Todo>>)result).getData();
            }
            else{
                receivedate.setValue(false);
            }
        });
    }


    public LiveData<String> getName(){
        return loggedname;
    }

    public void setName(String name){
        loggedname.setValue(name);
    }

    public LiveData<String> getDate() {return choosedate;}

    public void setDate(String date) {choosedate.setValue(date);}

    public List<Todo> getTodoList(){return todoList;}

    public LiveData<Boolean> getDoingWork(){return doingWork;}

    public LiveData<Boolean> isLoggedIn(){return loggedIn;}

    public LiveData<Boolean> registerSuccess() {return registerSuccess;}

    public LiveData<Boolean> sendTodoTextSuccess() {return sendTodoTextSuccess;}

    public LiveData<Boolean> receivingdate(){return receivedate;}


}
