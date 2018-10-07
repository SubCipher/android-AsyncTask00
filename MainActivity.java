package com.stepwisedesigns.androidasync;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText_time;
    private TextView finalResult;
    private static final String TAG = "Time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_time = findViewById(R.id.in_time);
        button = findViewById(R.id.btn_run);
        finalResult = findViewById(R.id.tv_result);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = editText_time.getText().toString();
                runner.execute(sleepTime);
            }
        });
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            publishProgress("Sleeping..."); //calls on progress up date

            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = " Slept for " + params[0] + " seconds";

                Log.d(TAG, "Timer: " + time);

            } catch (InterruptedException e) {

                e.printStackTrace();
                resp = e.getMessage();

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();

            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result){

            //execution of result for time consuming operation
            progressDialog.dismiss();
            finalResult.setText(result);
        }

        @Override
        protected void onPreExecute(){

            progressDialog = ProgressDialog.show(MainActivity.this, "ProgressDialog",
                    "Wait for " + editText_time.getText().toString()+ " seconds");
        }

        @Override
        protected void onProgressUpdate(String... text){

            finalResult.setText(text[0]);
        }


    }

}