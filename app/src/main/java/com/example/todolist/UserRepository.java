package com.example.todolist;

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
    public void setDataSource(FirebaseDataSource ds) {this.firebaseDataSource = ds;}

}
