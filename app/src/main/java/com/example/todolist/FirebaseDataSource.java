package com.example.todolist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todolist.todo.Todo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataSource {
   private FirebaseFirestore db = FirebaseFirestore.getInstance();

   //회원가입기능 - 인자로 받은거 각각 user에 저장
   public void tryRegister(String id, String password, String name, DataSourceCallback<Result> callback){
       Map<String, Object> user = new HashMap<>();
       user.put("userId", id);
       user.put("Password",password);
       user.put("Name", name);

       //users라는 collection에, document는 id값으로 user에 적어놓은거 저장할거야
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

   //로그인기능 - get으로 가져오는건데
    public void tryLogin(String id, String password, DataSourceCallback<Result> callback) {
       //users라는 collection에서 document는 id값으로 가져오는거
        db.collection("users")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    //가져오는거 성공하면 그 결과를 snapshot으로 찍고, 그게 존재하면 성공 없으면 실패
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

    //적은 내용을 저장하는건데 map에다가 적은내용이랑 누가적었는지 저장해서 collection이름은 date로 받은값으로, document는 text로 받은 값으로 해서 저장
    public void sendTodoText(String date,String name,String text, DataSourceCallback<Result> callback){
       HashMap<String,String> map = new HashMap<>();
       map.put("Todo", text);
       map.put("UserId", name);
       db.collection(date)
               .document(text)
               .set(map);
       callback.onComplete(new Result.Success("Success"));
    }

    //이제까지 적힌 todolist를 가져오는 건데, collection은 date값으로된거 가져올거야
    public void getTodoList(String date, DataSourceCallback<Result> callback){
        List<Todo> toReturn = new ArrayList<>();
        db.collection(date)
                .get()
                //가저오는게 성공하면 그거를 List snapshot으로 저장하고
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        List<DocumentSnapshot> snaps = task.getResult().getDocuments();
                        //그 저장된 snapshot 크기만큼 돌려서, (쓴 글, 누가썻는지)를 toAdd에 넣어, 성공하면 toReturn에 있는 값을 저장, 실패하면 error
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

    //삭제하는 기능인데, document이름은 text로 받은거고, 해당하는걸 delete할거야, 성공하면 success, 실패하면 failed할거야
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
