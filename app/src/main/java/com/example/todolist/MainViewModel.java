package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> loggedIn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();

//    public void tryLogin(String id,String password){
//        doingWork.setValue(true);
//        userRepository.tryLogin(id,password,result -> {
//            if(result.equals("Success")){
//                loggedIn.setValue(true);
//            }
//            doingWork.setValue(false);
//        });
//    }

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

    public LiveData<Boolean> getDoingWork(){return doingWork;}
    public LiveData<Boolean> isLoggedIn(){return loggedIn;}
    public LiveData<Boolean> registerSuccess() {return registerSuccess;}

}
