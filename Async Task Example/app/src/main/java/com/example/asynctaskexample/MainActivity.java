package com.example.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTime;
    private Button buttonStartTask;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTime = findViewById(R.id.et_time);
        textViewResult = findViewById(R.id.tv_result);
        buttonStartTask = findViewById(R.id.btn_startTask);

        buttonStartTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeAsyncTask task = new TimeAsyncTask();
                String time = editTextTime.getText().toString();
                task.execute(time);
            }
        });
    }

    private class TimeAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        private String response;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this,
                    "AsyncTask is Under Process",
                    "Wait for " + editTextTime.getText().toString() + " Seconds.");
        }

        @Override
        protected String doInBackground(String... strings) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(strings[0]) * 1000;

                Thread.sleep(time);
                response = strings[0];
            } catch (InterruptedException e) {
                e.printStackTrace();
                response = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textViewResult.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            textViewResult.setText("Async Task successfully completed in " + editTextTime.getText().toString() + " seconds.");
        }

        @Override
        protected void onCancelled() {
            textViewResult.setText("Unable to complete the task!!");
        }
    }
}

