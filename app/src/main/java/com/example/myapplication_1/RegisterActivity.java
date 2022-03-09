package com.example.myapplication_1;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity{
    Button button1,button2,button3;//완료,취소
    EditText shopname,title,content,text_url,min,Remain;
    int category_int;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        button1 =(Button) findViewById(R.id.reg_button); //전송
        button2=(Button) findViewById(R.id.cancel_button);//취소
        button3=(Button) findViewById(R.id.category); //카테고리 선택



        shopname=findViewById(R.id.shopname);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        text_url=findViewById(R.id.text_url);
        min=findViewById(R.id.min);
        Remain=findViewById(R.id.Remain);

        button1.setOnClickListener(new View.OnClickListener(){ //서버에 작성한 글 데이터 전송(완료버튼 클릭)
            @Override
            public void onClick(View view) {

                String namestore = shopname.getText().toString();
                String postTitle = title.getText().toString();
                String postcontent = content.getText().toString();
                String posturl = text_url.getText().toString();
                String ordermin_1 = min.getText().toString();
                String remain_1 = Remain.getText().toString();
                int ordermin=0;
                int remain = 0;
                if(ordermin_1.length()>1){
                 ordermin = Integer.parseInt(ordermin_1);
                 remain = Integer.parseInt(remain_1);
                }
                //문자열 앞뒤 공백 제거
                String namestore_s=namestore.trim();
                String postTitle_s=postTitle.trim();
                String postcontent_s=postcontent.trim();
                //카테고리 무조건 선택

                if (namestore_s.length() < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.Base_Theme_AppCompat_Dialog_Alert);
                    builder.setMessage("가게이름은 최소 1자 이상이어야 합니다.");
                    builder.show();
                } else if (postTitle_s.length() <= 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.Base_Theme_AppCompat_Dialog_Alert);
                    builder.setMessage("제목은 최소 2자 이상이어야 합니다.");
                    builder.show();
                } else if (postcontent_s.length() < 5) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.Base_Theme_AppCompat_Dialog_Alert);
                    builder.setMessage("내용은 최소 5자 이상이어야 합니다.");
                    builder.show();
                }
                else if(ordermin<remain){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.Base_Theme_AppCompat_Dialog_Alert);
                    builder.setMessage("남은최소주문금액은 최소주문금액을 넘을수 없습니다.");
                    builder.show();
                }
                else {

                    // url 생성
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ktl-last.herokuapp.com/posts").newBuilder();
                    String url = urlBuilder.build().toString();

                    // json 객체 생성
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("nameStore", namestore);
                        obj.put("postCategory", category_int + 1);
                        obj.put("postTitle", postTitle);
                        obj.put("postContent", postcontent);
                        obj.put("postURL", posturl);
                        obj.put("costOrderMin", ordermin);
                        obj.put("costOrderRemain", remain);
                        obj.put("userID", "test2"); //추후 마스킹
                        obj.put("userName", "Ho");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = RequestBody.create(
                                MediaType.parse("application/json; charset=utf-8"),
                                obj.toString()
                        );
                        Request request = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .build();

                        // 응답 콜백
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println("Error is" + e.toString());
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                System.out.println("Response Body is " + response.body().string());
                                if (response.isSuccessful()) {
                                    System.out.println("응답 성공");
                                    Intent intent = new Intent(
                                            getApplicationContext(),
                                            postlistActivity.class); //넘어갈 다음 화면
                                    startActivity(intent);
                                    finish();
                                } else {
                                    System.out.println("응답 실패");
                                }
                            }
                        });

                    } catch (Exception e) {
                        System.err.println(e.toString());
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() { //취소 버튼
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){ //카테고리 선택 버튼
            @Override
            public void onClick(View view){
                final String[] items = new String[]{"1인분", "한식", "분식", "카페,디저트", "돈까스,회,일식", "치킨", "피자", "아시안,양식","중국집","족발,보쌈","야식","찜,탕","도시락","패스트푸트"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("카테고리를 선택하세요.")
                        .setSingleChoiceItems(items,
                                0,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedIndex[0] = which;
                                        category_int=which;
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterActivity.this, items[selectedIndex[0]], Toast.LENGTH_SHORT).show();
                                button3.setText(items[category_int]);
                            }
                        }).create().show();

            }
        });





    }

    }

