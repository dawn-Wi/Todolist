package com.example.todolist;

import android.util.Log;

public class UserRepository {
    private static volatile UserRepository INSTANCE = new UserRepository();
    public static UserRepository getInstance(){return INSTANCE;}
    private FirebaseDataSource firebaseDataSource;

    public void tryRegister(final String id, final String password, final String name, final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.tryRegister(id, password,name,result -> {
            if(result instanceof Result.Success){
                callback.onComplete("Success");
            }
            else{
                callback.onComplete(((Result.Error)result).getError().getMessage());
            }
        });
    }

    public void tryLogin(final String id, final String password, final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.tryLogin(id, password, result -> {
            if(result instanceof Result.Success){
                callback.onComplete("Success");
            }
            else{
                callback.onComplete(((Result.Error)result).getError().getMessage());
            }
        });
    }

    public void sendTodoText(final String date,final String name,final String text, final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.sendTodoText(date, name,text, result->{
            if(result instanceof Result.Success){
                callback.onComplete("Success");
                Log.d("repository", "sendTodoText Success");
            }
            else{
                callback.onComplete(((Result.Error) result).getError().getMessage());
                Log.d("repository", "sendTodoText Failed");
            }
        });
    }

    public void getTodoList(final String date, final UserRepositoryCallback callback){
        firebaseDataSource.getTodoList(date, result -> {
            if(result instanceof Result.Success){
                callback.onComplete(result);
            }
        });
    }

    public void deleteTodoText(final String date, final String text, final FirebaseDataSource.DataSourceCallback<String> callback){
        firebaseDataSource.deleteTodoText(date, text, result -> {
            if(result instanceof Result.Success){
                callback.onComplete("Success");
                Log.d("repository", "deleteTodoText Success");
            }
            else {
                callback.onComplete(((Result.Error) result).getError().getMessage());
                Log.d("repository", "deleteTodoText Failed");
            }
        });
    }

    public void setDataSource(FirebaseDataSource ds) {this.firebaseDataSource = ds;}

    public interface UserRepositoryCallback<T>{
        void onComplete(Result result);
    }
}
