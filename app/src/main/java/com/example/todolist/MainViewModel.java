package com.example.todolist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.todo.Todo;

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

    //로그인 기능 - 일단 doingWork의 값을 true로 변경, 성공시 loggedIn 값을 true로 변경, 이후 doingWork값을 false로 변경
    public void tryLogin(String id,String password){
        doingWork.setValue(true);
        userRepository.tryLogin(id,password,result -> {
            if(result.equals("Success")){
                loggedIn.setValue(true);
            }
            doingWork.setValue(false);
        });
    }

    //회원가입 기능 - 일단 doingWork의 값을 true로 변경, 성공시 registerSuccess의 값을 true로, 실패하면 false로, 이후 doingWork값을 false로 변경
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

    //쓴 todo문장 보내기 - 성공시 sendTodoTextSuccess의 값을 true로하고 loadTodoList(date)실행, 실패시 sendTodoTextSuccess의 값을 false로
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

    //해당날짜에 적힌 todoList 가져오기 - 성공시 todoList에 getData로 가져온 데아터 저장, listLoaded값 true로 변경, 실패시 listLoaded값 false로
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

    //삭제하는 기능 - 성공시 loadTodoList(date) 실행(:리스트 가져오기) & 해당 로그띄우기
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
