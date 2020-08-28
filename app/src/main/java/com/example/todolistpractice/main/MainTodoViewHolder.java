package com.example.todolistpractice.main;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistpractice.R;
import com.example.todolistpractice.data.entity.TodoItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class MainTodoViewHolder extends RecyclerView.ViewHolder {
    private TextView todo_tv_title;
    private CheckBox todo_cb;
    private TextView todo_tv_due;
    private TextView todo_tv_left;

    public MainTodoViewHolder(View itemView) {
        super(itemView);

        todo_tv_title = itemView.findViewById(R.id.todo_tv_title);
        todo_cb = itemView.findViewById(R.id.todo_cb);
        todo_tv_due = itemView.findViewById(R.id.todo_tv_due);
        todo_tv_left = itemView.findViewById(R.id.todo_tv_left);


    }

    public void onBind(TodoItem item) throws ParseException {


        todo_tv_title.setText(item.getTitle());
        todo_cb.setChecked(item.getChecked());
        todo_tv_due.setText(item.getDue_date());


        if (item.getChecked()) {
            SpannableString contentSp = new SpannableString(item.getTitle());
            contentSp.setSpan(new StrikethroughSpan(), 0,
                    item.getTitle().length(), 0);
            todo_tv_title.setText(contentSp);
        }


        //날짜 계산

        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH) + 1;
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dDate = simpleDataFormat.parse(item.getDue_date());
        todo_tv_left.setText("DDDD");
        Date today = simpleDataFormat.parse(mYear + "/" + mMonth + "/" + mDay);
        Long left = -(dDate.getTime() - today.getTime()) / (24 * 60 * 60 * 1000);


        todo_tv_left.setText("D" + left.toString());

        if (left.intValue() > 0) {
            todo_tv_left.setTextColor(Color.RED);
            todo_tv_left.setText("D+" + left.toString());
        }
        if (left.intValue() == 0) {
            todo_tv_left.setTextColor(Color.RED);
            todo_tv_left.setText("D-Day");
        }

    }
}
