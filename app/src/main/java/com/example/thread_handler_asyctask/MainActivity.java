package com.example.thread_handler_asyctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtClock1,txtClock2;
    Button btnStart;
    Handler handler1,handler2;
    public static final int NUMBER_1=1;
    public static final int NUMBER_1_DONE=0;
    public static final int NUMBER_2=1;
    public static final int NUMBER_2_DONE=0;
    public boolean isUpdate1=false;
    public boolean isUpdate2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        updateNumber1();
        updateNumber2();
    }

    private void updateNumber1() {
        handler1=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case NUMBER_1:
                        isUpdate1=true;
                        txtClock1.setText(String.valueOf(msg.arg1));
                        break;
                    case NUMBER_1_DONE:
                        isUpdate1=false;
                        txtClock1.setText("DONE1");
                        break;
                    default:break;
                }
            }
        };
    }

    private void updateNumber2() {
        handler2=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case NUMBER_2:
                        isUpdate2=true;
                        txtClock2.setText(String.valueOf(msg.arg1));
                        break;
                    case NUMBER_2_DONE:
                        isUpdate2=false;
                        txtClock2.setText("DONE2");
                        break;
                    default:break;
                }
            }
        };
    }

    private void getViews(){
        txtClock1=(TextView)findViewById(R.id.txtClock1);
        txtClock2=(TextView)findViewById(R.id.txtClock2);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                if(!isUpdate1&&!isUpdate2){
                    countNumber1();
                    countNumber2();
                }
                break;
            default:break;
        }
    }

    private void countNumber1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=10;i++) {
                    Message message = new Message();
                    message.what = NUMBER_1;
                    message.arg1 = i;
                    handler1.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler1.sendEmptyMessage(NUMBER_1_DONE);
            }
        }).start();
    }

    private void countNumber2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=10;i++) {
                    Message message = new Message();
                    message.what = NUMBER_2;
                    message.arg1 = i;
                    handler2.sendMessage(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler2.sendEmptyMessage(NUMBER_2_DONE);
            }
        }).start();
    }
}