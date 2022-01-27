package com.example.todolist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> loggedIn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> sendTodoTextSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loggedname = new MutableLiveData<>();
    private MutableLiveData<Boolean> listChanged = new MutableLiveData<>();
    private MutableLiveData<Boolean> listLoaded = new MutableLiveData<>(false);
    private MutableLiveData<String> writeText = new MutableLiveData<>();
    private String selectedDate;
    //private String

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
                loadTodoList(date);
            }
            else{
                sendTodoTextSuccess.postValue(false);
                Log.d("DEBUG", "sendTodoText Failed");
            }
        });
    }

    public void loadTodoList(String date){
        userRepository.getTodoList(date, result->{
            if(result instanceof Result.Success){
                todoList = ((Result.Success<List<Todo>>)result).getData();
                listLoaded.setValue(true);
            }
            else{
                listLoaded.setValue(false);
            }
        });
    }

    public void deleteTodoText(String date, String text){
        userRepository.deleteTodoText(date, text, result -> {
            if(result.equals("Success")){
                Log.d("DEBUG", "deleteTodoText Success");
                loadTodoList(date);
            }
            else {
                Log.d("DEBUG", "deleteTodoText Failed");
            }
        });
    }

    public LiveData<String> getName(){ return loggedname; }

    public void setName(String name){
        loggedname.setValue(name);
    }

    public String getDate() {return selectedDate;}

    public void setDate(String date) {selectedDate=date;}

    public LiveData<String> getWriteText() {return writeText;}

    public void setWriteText(String text) {writeText.setValue(text);}

    public LiveData<Boolean> isListChanged() { return listChanged;}

    public List<Todo> getTodoList(){return todoList;}

    public LiveData<Boolean> getDoingWork(){return doingWork;}

    public LiveData<Boolean> isLoggedIn(){return loggedIn;}

    public LiveData<Boolean> registerSuccess() {return registerSuccess;}

    public LiveData<Boolean> sendTodoTextSuccess() {return sendTodoTextSuccess;}

    public LiveData<Boolean> isListLoaded(){return listLoaded;}


}
