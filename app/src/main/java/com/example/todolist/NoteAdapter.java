package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    //아이템이 들어갈 배열 정의
    ArrayList<Note> items = new ArrayList<Note>();

    //ViewHolder의 역할(아이템들을 가지고 있는 역할을 하는 것)을 하기 위해
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout item_ly;
        CheckBox item_cb_checkbox;
        Button item_bt_deletebutton;

        public ViewHolder(View itemView){
            super(itemView);

            item_ly = itemView.findViewById(R.id.item_ly);
            item_cb_checkbox = itemView.findViewById(R.id.item_cb_checkbox);
            item_bt_deletebutton = itemView.findViewById(R.id.item_bt_deletebutton);

            item_bt_deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String TODO = (String) item_cb_checkbox.getText();
                    deleteToDo(TODO);
                    Toast.makeText(view.getContext(),"삭제",Toast.LENGTH_SHORT).show();

                }

                private void deleteToDo(String TODO){

                }
            });

        }

        //EditText에서 입력받은 checkBox의 텍스트를 checkbox의 Text에 넣을 수 있게 하는거
        public void setItem(Note item){
            item_cb_checkbox.setText(item.getTodo());
        }
        //아이템들을 담은 LinearLayout을 보여주게하는 메서드
        public void setLayout(){
            item_ly.setVisibility(View.VISIBLE);
        }

    }
    //배열에 있는 item들을 가리키는 메서드 정의
    public void setItems(ArrayList<Note> items){
        this.items = items;
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        //todo-item.xml을 인플레이션하는 코드
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
