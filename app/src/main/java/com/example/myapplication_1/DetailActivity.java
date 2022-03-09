package com.example.myapplication_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailActivity extends AppCompatActivity{
    TextView category,shopname,title,content,URL,min,Remain,postTime,userName_1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int postNum_11=intent.getExtras().getInt("postNum");
        //System.out.println(postNum_11);

        TextView textview = new TextView(this);
        // url 생성
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ktl-last.herokuapp.com/posts/"+postNum_11).newBuilder();

        String url = urlBuilder.build().toString();

        // json 객체 생성
        JSONObject obj = new JSONObject();
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    obj.toString()
            );
            Request request = new Request.Builder()
                    .url(url)
                    .get()
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
                    //System.out.println("Response Body is "+response.body().string());
                    if (response.isSuccessful()) {
                        System.out.println("응답 성공");
                        ResponseBody body=response.body();
                        try{
                            final String[] items = new String[]{"1인분", "한식", "분식", "카페,디저트", "돈까스,회,일식", "치킨", "피자", "아시안,양식","중국집","족발,보쌈","야식","찜,탕","도시락","패스트푸트"};

                            assert body != null;
                            String list_content = body.string();
                            //json 파싱
                            JSONParser parser = new JSONParser();
                            JSONObject object =(JSONObject) parser.parse(list_content);
                            JSONArray pares_data =(JSONArray) object.get("data");
                            JSONObject pares_dataList=(JSONObject) pares_data.get(0);
                            JSONArray pares_postArray=(JSONArray) pares_dataList.get("content");
                            JSONArray pares_postArray_2=(JSONArray) pares_dataList.get("comment");


                            JSONObject pares_content=(JSONObject) pares_postArray.get(0);
                            long postNum1=(long)pares_content.get("postNum");
                            int postNum=(int) postNum1;
                            String nameStore=(String)pares_content.get("nameStore");
                            long postCategory_1=(long)pares_content.get("postCategory");
                            String postTitle=(String)pares_content.get("postTitle");
                            String postContent=(String)pares_content.get("postContent");
                            String postURL=(String)pares_content.get("postURL");
                            long postState=(long)pares_content.get("postState");
                            String postUpTime=(String)pares_content.get("postUpTime");
                            long costOrderMin_1=(long)pares_content.get("costOrderMin");
                            long costOrderRemain_1=(long)pares_content.get("costOrderRemain");
                            String costOrderMin=String.valueOf(costOrderMin_1);
                            String costOrderRemain=String.valueOf(costOrderRemain_1);
                            String userName=(String)pares_content.get("userName");
                            String userID=(String)pares_content.get("userID");
                            int postCategory=(int) postCategory_1;
                            postCategory--;

                            /*
                            System.out.println(nameStore);
                            System.out.println(postTitle);
                            System.out.println(postContent);
                            System.out.println(postURL);
                            System.out.println(costOrderMin);
                            System.out.println(costOrderRemain);
                            */
                            category=(TextView) findViewById(R.id.category_detail);
                            shopname=(TextView) findViewById(R.id.shopname_detail);
                            title=(TextView) findViewById(R.id.title_detail);
                            content=(TextView) findViewById(R.id.content_detail);
                            URL=(TextView) findViewById(R.id.text_url_detail);
                            min=(TextView) findViewById(R.id.min_detail);
                            Remain=(TextView) findViewById(R.id.Remain_detail);
                            postTime=(TextView) findViewById(R.id.postTime_detail);
                            userName_1=(TextView) findViewById(R.id.userName_detail);

                            int finalPostCategory = postCategory;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    category.setText(items[finalPostCategory]);
                                    shopname.setText(nameStore);
                                    title   .setText(postTitle);
                                    content .setText(postContent);
                                    URL    .setText(postURL);
                                    min    .setText(costOrderMin);
                                    Remain  .setText(costOrderRemain);
                                    postTime.setText(postUpTime);
                                    userName_1.setText(userName);
                                }
                            });



                        }
                        catch (IOException | ParseException e){
                            e.printStackTrace();
                        }


                    } else {
                        System.out.println("응답 실패");
                    }

                }
            });

        } catch (Exception e) {
            System.err.println(e.toString());
        }



        setContentView(R.layout.activity_detail);




    }

}
