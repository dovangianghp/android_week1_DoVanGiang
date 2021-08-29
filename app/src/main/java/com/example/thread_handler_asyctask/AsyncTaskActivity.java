package com.example.thread_handler_asyctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    Button btnStart_Progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        getViews();
    }
    private void getViews(){
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        btnStart_Progress=(Button)findViewById(R.id.btnStart_Progress);
        btnStart_Progress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart_Progress:
                new AsyncTaskClass().execute();
                break;
            default:break;
        }
    }
    private class AsyncTaskClass extends AsyncTask<Void, Integer, String>{

        @Override
        protected String doInBackground(Void... voids) {
            for(int i=0;i<100;i++)
            {
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(AsyncTaskActivity.this,s,Toast.LENGTH_LONG).show();
        }
    }
}