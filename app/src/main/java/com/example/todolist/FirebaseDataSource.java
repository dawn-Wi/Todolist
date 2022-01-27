package com.example.todolist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FirebaseDataSource {
   private FirebaseFirestore db = FirebaseFirestore.getInstance();

   public void tryRegister(String id, String password, String name, DataSourceCallback<Result> callback){
       Map<String, Object> user = new HashMap<>();
       user.put("userId", id);
       user.put("Password",password);
       user.put("Name", name);

       db.collection("users")
               .document(id)
               .set(user)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       callback.onComplete(new Result.Success<String>("Success"));
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       callback.onComplete(new Result.Error(new Exception("Failed")));
                   }
               });
   }

    public void tryLogin(String id, String password, DataSourceCallback<Result> callback) {
        db.collection("users")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                callback.onComplete(new Result.Success<String>("Success"));
                            }
                            else {
                                callback.onComplete(new Result.Error(new Exception("Failed")));
                            }
                        }
                        else{
                            callback.onComplete(new Result.Error(new Exception("Failed")));
                        }
                    }
                });

    }

    public void sendTodoText(String date,String name,String text, DataSourceCallback<Result> callback){
       HashMap<String,String> map = new HashMap<>();
       map.put("Todo", text);
       map.put("UserId", name);
       db.collection(date)
               .document(text)
               .set(map);
       callback.onComplete(new Result.Success("Success"));
    }

    public void getTodoList(String date, DataSourceCallback<Result> callback){
        List<Todo> toReturn = new ArrayList<>();
        db.collection(date)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                        for(int i=0;i<snaps.size();i++){
                            Todo toAdd = new Todo((snaps.get(i).getString("Todo")), snaps.get(i).getString("UserId"));
                            toReturn.add(toAdd);
                            Log.d("정보가지고오는거ㅓㅓㅓ", "getTodoText: "+toAdd);
                        }
                        Log.d("datasource", "onSuccess: firestore finish");
                        callback.onComplete(new Result.Success<List<Todo>>(toReturn));
                    }else {
                        Log.d("datasource", "onSuccess: firestore not finish");
                        callback.onComplete(new Result.Error(task.getException()));
                    }
                });
    }

    public void deleteTodoText(String date,String text, DataSourceCallback<Result> callback){
       db.collection(date)
               .document(text)
               .delete()
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       callback.onComplete(new Result.Success<String>("Success"));
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       callback.onComplete(new Result.Error(new Exception("Failed")));
                   }
               });
    }

    public interface DataSourceCallback<T>{
        void onComplete(T result);
    }
}
