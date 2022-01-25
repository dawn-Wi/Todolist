package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

public class CalendarFragment extends Fragment {
    MainViewModel mainViewModel;
    TextView calendar_tv_name;
    TextView calendar_tv_text;
    CalendarView calendar_cv;
    String displayname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        calendar_tv_name = view.findViewById(R.id.calendar_tv_name);
        calendar_tv_text = view.findViewById(R.id.calendar_tv_text);
        calendar_cv = view.findViewById(R.id.calendar_cv);
        //calendar_tv_name에다가 userId를 넣고 싶은데 안되는 중
//        mainViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                if(s!=null){
//                    calendar_tv_name.setText(s);
//                }
//            }
//        });
//        calendar_tv_name.setText(displayname);

        calendar_cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth) {
                String date = year+ "/"+(month+1)+"/"+dayofMonth;
                Log.d("asdf", "onSelectedDayChange: "+date);
                NavHostFragment.findNavController(CalendarFragment.this).navigate(R.id.action_calendarFragment_to_todoFragment);
            }
        });
    }
}