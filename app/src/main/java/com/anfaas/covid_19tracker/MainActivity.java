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
import java.util.regex.Pattern;

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

                 String[] jsons=response.split(",\"regional\":");
                 String regions   =  jsons[1];
                 String[] regions_final=regions.split(Pattern.quote("},\"last"));
                 String json_regions=regions_final[0];

                 String[] summary =jsons[0].split("\"summary\":");
                 String json_summary=summary[1];
                 GsonBuilder gsonBuilder=new GsonBuilder();
                 Gson gson =gsonBuilder.create();
                 Summry summry=gson.fromJson(json_summary,Summry.class);
                 Log.d(TAG, "onResponse: "+summry.getConfirmedCasesIndian());
                 Log.d(TAG, "onResponse: "+json_regions.trim());
                 GsonBuilder builder=new GsonBuilder();
                 Gson gson1= builder.create();
              Regional[] regionals=  gson1.fromJson(json_regions,Regional[].class);
              for (Regional regional: regionals)
              {
                  Log.i(TAG, "onResponse: "+regional.getLoc());
              }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.e(TAG, "onErrorResponse:",error );
             }
         });

queue.add(request);
    }
}
