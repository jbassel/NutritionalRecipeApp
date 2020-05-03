package com.jacobbassel.nutritionalrecipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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



public class IngredientsActivity extends AppCompatActivity {
    String id;
    String queryFinder;
    int count = 0;
    ArrayList<String> SavedId = new ArrayList<String>();
    ArrayList<String> Name = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et = findViewById(R.id.edit_text);
        TextView text = findViewById(R.id.text_view);

        loader(text);


        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);
                queryFinder = "query";

                id = et.getText().toString();
                doDownload();
            }

        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);
                queryFinder = "excludeIngredients";

                id = et.getText().toString();
                doDownload();
            }

        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);


                SavedId.add(et.getText().toString());

            }
        });

    }

    public void loader(TextView text2) {
        SharedPreferences pref1 = getSharedPreferences("MyPref1", 0);
        text2.setText("");

        int size = pref1.getInt("arraySize", 0);

        for (int i = 0; i < size; i++) {
            Name.add(pref1.getString( "thing" + i, "Saved ID's for future reference"));
            SavedId.add(Name.get(i));
        }
    }

    private StackExchangeDownload dataDownload;

    private void doDownload() {
        if (dataDownload == null) {
            dataDownload = new StackExchangeDownload();
            dataDownload.execute();
        }
    }


    private class StackExchangeDownload extends AsyncTask<Void, Void, ResultData> {
        @Override
        protected ResultData doInBackground(Void... voids) {
            ResultData resultData = new ResultData();


            try {

                Uri.Builder builder = Uri.parse("https://api.spoonacular.com/recipes/search?").buildUpon();
                builder.appendQueryParameter(queryFinder, id);
                builder.appendQueryParameter("apiKey", getResources().getString(R.string.api_key));

                URL url = new URL(builder.toString());

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder jsonData = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }

                StringBuilder titleBuilder = new StringBuilder();

                JSONObject reader = new JSONObject(jsonData.toString());

                JSONArray items = reader.getJSONArray("results");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);

                    String idnum = item.getString("id");
                    titleBuilder.append(idnum + "     ");

                    String title = item.getString("title");
                    titleBuilder.append(title);
                    titleBuilder.append("\n" + "-------------------------------------------------------------------------------" + "\n");

                }

                resultData.titleStr = titleBuilder.toString();

                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultData;
        }

        @Override
        protected void onPostExecute(ResultData resultData) {
            TextView tv = findViewById(R.id.text_view);
            tv.setText(resultData.titleStr);


            dataDownload = null;
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences sp = getSharedPreferences("MyPref1", 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("arraySize", SavedId.size());

        for (int i = 0; i < SavedId.size(); i++) {
            editor.putString("thing" + i, SavedId.get(i));
        }

        editor.commit();
    }

    private class ResultData {
        String titleStr = "";
    }
}