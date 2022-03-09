package com.example.myapplication_1;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class SingleItemView extends LinearLayout {
TextView textView_title,textView_num,textView_time,textView_store;
ArrayList<String> Items;
    public SingleItemView(Context context) {
        super(context);
        init(context);
    }
    public SingleItemView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }


    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.content,this, true);
        textView_title = findViewById(R.id.postTitle_1);
        textView_num = findViewById(R.id.postNum_1);
        textView_time = findViewById(R.id.postTime_1);
        textView_store=findViewById(R.id.storeName_1);
    }

    public void setPostTitle(String postTitle){

        textView_title.setText(postTitle);
    }
    public void setStoreName(String storeName){

        textView_store.setText(storeName);
    }
    public void setPostTime(String postTime){

        textView_time.setText(postTime);
    }
    public void setPostNum(int postNum){

        textView_num.setText(String.valueOf(postNum));
    }

}
