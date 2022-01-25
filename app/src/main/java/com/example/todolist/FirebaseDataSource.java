package com.example.todolist;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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


//    public void tryLogin(String id, String password, DataSourceCallback<Result> callback) {
//        db.collection("users")
//                .document(id)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
////                        if(task.getResult()!=null){
////                            callback.onComplete(new Result.Success<String>("Success"));
////                        }
////                        else{
////                            callback.onComplete(new Result.Error(task.getException()));
////                        }
//                    }
//                });
    }

    public interface DataSourceCallback<T>{
        void onComplete(T result);
    }
}
