package com.example.todolistpractice.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;


import com.example.todolistpractice.R;
import com.example.todolistpractice.data.database.MyDatabase;
import com.example.todolistpractice.data.entity.TodoItem;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTodoActivity extends AppCompatActivity {

    private TextInputLayout til_title, til_sDate, til_dDate, til_memo;
    private ImageButton ib_sDate, ib_dDate;
    private final int START_DATE = 0;
    private final int DUE_DATE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        til_title = findViewById(R.id.add_til_todo);
        til_sDate = findViewById(R.id.add_til_start_date);
        til_dDate = findViewById(R.id.add_til_due_date);
        til_memo = findViewById(R.id.add_til_memo);

        ib_sDate = findViewById(R.id.add_ibtn_start_date);
        ib_dDate = findViewById(R.id.add_ibtn_due_date);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Todo");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        ib_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(START_DATE);

            }
        });

        ib_dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(DUE_DATE);
            }
        });

        til_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(START_DATE);
            }
        });

        til_dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(DUE_DATE);
            }
        });
    }

    private void showCalender(final int mode) {

        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String month_s = new Integer(month+1).toString();
                String day_s = new Integer(dayOfMonth).toString();
                if(month + 1< 10) month_s = "0" + month_s;
                if(dayOfMonth <10 )day_s = "0" + day_s;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String date = year + "-" + month_s + "-" + day_s;
                if (mode == START_DATE) {
                    til_sDate.getEditText().setText(date);
                } else if (mode == DUE_DATE) {
                    til_dDate.getEditText().setText(date);
                } else {/*TODO:Error */ }

            }
        }, mYear, mMonth, mDay).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save_todo:
                String title = til_title.getEditText().getText().toString();
                String sDate = til_sDate.getEditText().getText().toString();
                String dDate = til_dDate.getEditText().getText().toString();
                String memo = til_memo.getEditText().getText().toString();



                if (title.equals(""))
                    til_title.setError("필수 요소입니다.");
                else
                    til_title.setError(null);

                if (sDate.equals(""))
                    til_sDate.setError("필수 요소입니다.");
                else
                    til_sDate.setError(null);

                if (dDate.equals(""))
                    til_dDate.setError("필수 요소입니다.");
                else
                    til_dDate.setError(null);

                if(!title.equals("") && !sDate.equals("") && !dDate.equals("")){
                    if(sDate.compareTo(dDate)>0){
                        til_sDate.setError("시작 날짜가 더 느려요!!");
                        til_dDate.setError("끝나는 날짜가 더 빨라요!!");
                    }
                    else {

                        TodoItem newItem = new TodoItem(title, dDate, sDate, memo);
                        MyDatabase myDatabase = MyDatabase.getInstance(AddTodoActivity.this);
                        myDatabase.todoDao().insertTodo(newItem);

                        finish();
                        break;
                    }

                }


        }
        return super.onOptionsItemSelected(item);
    }
}