package com.anfaas.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         RequestQueue queue = Volley.newRequestQueue(this);
         String url="https://api.rootnet.in/covid19-in/stats/latest";
         StringRequest request=new StringRequest(url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 Log.d(TAG, "onResponse: "+response);
                 GsonBuilder gsonBuilder=new GsonBuilder();
                 Gson gson= gsonBuilder.create();
              Data[] regionals=  gson.fromJson(response.trim(),Data[].class);

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.e(TAG, "onErrorResponse: ",error );
             }
         });

queue.add(request);
    }
















    public  class task extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... strings)  {
            JSONObject json = null;
            try {
                json = readJsonFromUrl("https://swapi.co/api/people/1/");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(json.toString());
            try {
                System.out.println(json.get("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        private  String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public  JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
        }
    }

}
