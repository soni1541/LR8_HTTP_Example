package com.example.lr8_http_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn_next_fact;
    private TextView text_fact;

    private TextView number_fact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_next_fact = findViewById(R.id.btn_next_fact);
        text_fact = findViewById(R.id.text_fact);
        number_fact = findViewById(R.id.text_number);

        btn_next_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpHandler httpHandler = new OkHttpHandler();
                httpHandler.execute();
            }
        });
    }

    public class OkHttpHandler extends AsyncTask <Void, Void, String>{

        @Override
        protected String doInBackground(Void ... params) {
            Request.Builder builder = new Request.Builder();
            Request request = builder.url("https://catfact.ninja/fact").build(); //.method()
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());

                return jsonObject.getString("fact") + "\n"+ jsonObject.getString("length");

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            text_fact.setText(o.toString());
        }
    }
}