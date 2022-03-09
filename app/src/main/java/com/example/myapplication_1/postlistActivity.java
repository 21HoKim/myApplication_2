package com.example.myapplication_1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class postlistActivity extends AppCompatActivity {


Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        class SingleAdapter extends BaseAdapter {
            ArrayList<SingleItem> items = new ArrayList<SingleItem>();
            @Override public int getCount() {
                return items.size();
            }
            public void addItem(SingleItem item){
                items.add(item);
            }
            @Override public Object getItem(int position) {
                return items.get(position);
            }
            @Override public long getItemId(int position) {
                return position;
            }
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                SingleItemView singleItemView = null;
                if(convertView == null) {
                    singleItemView = new SingleItemView(getApplicationContext());
                }
                else {
                    singleItemView = (SingleItemView)convertView;
                }
                SingleItem item = items.get(position);
                singleItemView.setPostTitle((item.getPostTitle()));
                singleItemView.setStoreName(item.getStoreName());
                singleItemView.setPostTime(item.getPostTime());
                singleItemView.setPostNum(item.getPostNum());

                return singleItemView;
            }
        }
ArrayList<Integer> postNumList=new ArrayList<Integer>();
        //-----------------------------------------------통신
        // url 생성
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://ktl-last.herokuapp.com/posts").newBuilder();
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
                            assert body != null;
                            String list_content = body.string();
                            //json 파싱
                            JSONParser parser = new JSONParser();
                            JSONObject object =(JSONObject) parser.parse(list_content);
                            JSONArray pares_data =(JSONArray) object.get("data");
                            JSONObject pares_postList=(JSONObject) pares_data.get(0);
                            JSONArray pares_postArray=(JSONArray) pares_postList.get("postList");
                            JSONArray pares_categoryArray=(JSONArray) pares_postList.get("category");
                            ListView listView=findViewById(R.id.listView);
                            SingleAdapter adapter = new SingleAdapter();
                            for(int i = 0; pares_postArray.size() > i; i++){
                                JSONObject content=(JSONObject) pares_postArray.get(i);
                               // String postNum2=(String)content.get("postNum");
                                long postNum1=(long)content.get("postNum");
                               // int postNum= Integer.parseInt(postNum2);
                                int postNum=(int) postNum1;
                                postNumList.add(postNum);
                                long postCategory=(long)content.get("postCategory");
                                String nameStore=(String)content.get("nameStore");
                                String postTitle=(String) content.get("postTitle");
                                long postState=(long)content.get("postState");
                                String postUpTime=(String) content.get("postUpTime"); //파싱끝


                                adapter.addItem(new SingleItem(postTitle,nameStore,postUpTime,postNum));

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });

                            for(int i=0;i<pares_categoryArray.size();i++){
                                JSONObject category=(JSONObject) pares_categoryArray.get(i);
                                long postcategory_1=(long)category.get("postCategory");
                                int postcategory=(int)postcategory_1;
                                String categoryName=(String) category.get("categoryName");
                            }
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

        setContentView(R.layout.activity_postlist);

        button1=(Button) findViewById(R.id.reg_button); //글쓰기 버튼
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        RegisterActivity.class); //넘어갈 다음 화면
                startActivity(intent);
            }
        });
        ListAdapter adapter;
        SingleAdapter single = new SingleAdapter();
        ListView listView=(ListView) findViewById(R.id.listView);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    @Override
    public void onItemClick(AdapterView parent, View v, int position, long id){

        //System.out.println(single.getItem(position));

        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("postNum",postNumList.get(position));

        startActivity(intent);
        //System.out.println(postNumList.get(position));



    }
});



    }
}
