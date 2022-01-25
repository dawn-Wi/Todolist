package com.example.todolist;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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

    }

    public interface DataSourceCallback<T>{
        void onComplete(T result);
    }
}
