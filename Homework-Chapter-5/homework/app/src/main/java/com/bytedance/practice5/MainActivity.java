package com.bytedance.practice5;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bytedance.practice5.model.Message;
import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.socket.SocketActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Constants.STUDENT_ID);
            }
        });

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(null);
            }
        });
        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
            }
        });



    }

    //TODO 2
    // ???HttpUrlConnection????????????????????????????????????Gson?????????????????????UI?????????adapter.setData()?????????
    // ?????????????????????UI???????????????????????????????????????
    private void getData(String studentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (studentId == null) {
                    MessageListResponse message = baseGetReposFromRemote(
                            "","messages", "application/vnd+json");
                    if (message != null && (message.success)) {
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setData(message.feeds);
                            }
                        });
                    }
                } else {
                    MessageListResponse message = baseGetReposFromRemote(
                            studentId,"messages", "application/vnd+json");
                    if (message != null && (message.success)) {
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setData(message.feeds);
                            }
                        });
                    }
                }

            }
        }).start();
    }

    public MessageListResponse baseGetReposFromRemote(String id, String method, String accept) {
        String urlStr =
                Constants.BASE_URL + method + "?student_id=" + id;
        MessageListResponse result = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", accept);
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                result = new Gson().fromJson(reader, new TypeToken<MessageListResponse>() {
                }.getType());

                reader.close();
                in.close();
            } else {
                // ????????????
                Log.d("httpurl error", "httpurl error");
            }
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "????????????" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return result;
    }

}