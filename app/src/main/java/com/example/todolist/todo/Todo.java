package com.example.todolist.todo;

public class Todo {
    String userId;
    String todo;

    public Todo(String todo, String userId){
        this.userId = userId;
        this.todo = todo;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getTodo(){
        return todo;
    }
    public void setTodo(String todo){
        this.todo = todo;
    }
}
