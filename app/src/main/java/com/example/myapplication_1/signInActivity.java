package com.example.myapplication_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class signInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //접근 제어자 : private -> default -> protected -> public 순으로 보다 많은 접근을 허용한다
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.acti); -> 레이아웃 위치 수정 필요
        //setContentView - 화면전환

        Button button2 = (Button) findViewById(R.id.btn_signIn); //누르는 버튼

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        mainActivity.class); //넘어갈 다음 화면
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.btn_signIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // url 생성
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://poeynus.ddns.net:8080/users/login").newBuilder();
                String url = urlBuilder.build().toString();

                // json 객체 생성
                JSONObject obj = new JSONObject();
                try {
                    obj.put("userID", "test1");
                    obj.put("userPW", "test1");
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
                            System.out.println("Error is"+e.toString());
                            //e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            System.out.println("Response Body is "+response.body().string());
                            if (response.isSuccessful()) {
                                System.out.println("응답 성공");

                            } else {
                                System.out.println("응답 실패");
                            }
                        }
                    });

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        });

    }
}