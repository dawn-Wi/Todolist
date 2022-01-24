package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static volatile MainViewModel INSTANCE = new MainViewModel();
    private static MainViewModel getInstance() {return INSTANCE;}
    private FirebaseDataSource firebaseDataSource;

    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);

    public void tryLogin(String id,String password){
        doingWork.setValue(true);
        firebaseDataSource.tryLogin(id,password,result -> {
            if(result.equals("SUccess")){}
        });
    }
    public LiveData<Boolean> getDoingWork(){return doingWork;}
}
