package com.jacobbassel.nutritionalrecipeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SavedActivity extends AppCompatActivity{
    Context mContext;
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> id2 = new ArrayList<String>();
    String queryFinder;
    String thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_activity);
        TextView text2 = findViewById(R.id.text_view2);


        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text2 = findViewById(R.id.text_view2);

                loader(text2);

            }

        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text2 = findViewById(R.id.text_view2);

                loader2(text2);

            }

        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text2 = findViewById(R.id.text_view2);

                loader3(text2);

            }

        });


    }

    public void loader(TextView text2) {
        SharedPreferences pref1 = getSharedPreferences("MyPref1", 0);
        text2.setText("");

        int size = pref1.getInt("arraySize", 0);

        for (int i = 0; i <= size; i++) {
            id.add(pref1.getString("thing" + i, null));
            text2.setText(pref1.getString( "thing" + i, "Saved ID's for future reference") + "\n" + "------------------------------------------------------------------------------------------------" + "\n" + text2.getText());

        }
    }
    public void loader2(TextView text2) {
        SharedPreferences pref2 = getSharedPreferences("MyPref2", 0);
        text2.setText("");

        int size = pref2.getInt("arraySize2", 0);

        for (int i = 0; i <= size; i++) {
            id.add(pref2.getString("thing2" + i, null));
            text2.setText(pref2.getString( "thing2" + i, "Saved ID's for future reference") + "\n" + "------------------------------------------------------------------------------------------------" + "\n" + text2.getText());

        }
    }

    public void loader3(TextView text2) {
        SharedPreferences pref3 = getSharedPreferences("MyPref3", 0);
        text2.setText("");

        int size = pref3.getInt("arraySize3", 0);

        for (int i = 0; i <= size; i++) {
            id.add(pref3.getString("thing3" + i, null));
            text2.setText(pref3.getString( "thing3" + i, "Saved ID's for future reference") + "\n" + "------------------------------------------------------------------------------------------------" + "\n" + text2.getText());

        }
    }

}

