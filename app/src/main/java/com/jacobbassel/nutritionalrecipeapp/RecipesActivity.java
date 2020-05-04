package com.jacobbassel.nutritionalrecipeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RecipesActivity extends AppCompatActivity {
    String id;
    String queryFinder;
    String thing;
    ArrayList<String> savedData = new ArrayList<String>();
    ArrayList<String> savedIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);
        EditText et = findViewById(R.id.edit_text);

        Button search2 = findViewById(R.id.search_button);
        Button search3 = findViewById(R.id.button3);
        Button saved = findViewById(R.id.saved_button);
        Button leave = findViewById(R.id.button);


        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);
                thing = "";

                queryFinder = "/analyzedInstructions?";
                id = et.getText().toString();
                doDownload();
            }

        });

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);
                thing = "";

                queryFinder = "/ingredientWidget.json?";
                id = et.getText().toString();
                doDownload();
            }

        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thing = "";
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.edit_text);
                thing = "Save";
                doDownload();

            }
        });


    }


    public StackExchangeDownload dataDownload;

    public void doDownload() {
        if (dataDownload == null) {
            dataDownload = new StackExchangeDownload();
            dataDownload.execute();
        }
    }


    public class StackExchangeDownload extends AsyncTask<Void, Void, ResultData> {
        @Override
        protected ResultData doInBackground(Void... voids) {
            ResultData resultData = new ResultData();


            try {

                Uri.Builder builder = Uri.parse("https://api.spoonacular.com/recipes/" + id + queryFinder).buildUpon();
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

                if (queryFinder == "/ingredientWidget.json?") {

                    JSONObject reader = new JSONObject(jsonData.toString());

                    JSONArray items = reader.getJSONArray("ingredients");

                    titleBuilder.append("recipe Ingredients for id: " + id + "\n" + "------------------------------------------------------------------------------------------------" + "\n");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        JSONObject idnum = item.getJSONObject("amount");
                        String idnum2 = item.getString("name");

                        JSONObject title = idnum.getJSONObject("us");


                        String title2 = title.getString("unit");
                        String title3 = title.getString("value");
                        titleBuilder.append(title3 + " ");
                        titleBuilder.append(title2 + " of ");
                        titleBuilder.append(idnum2);


                        titleBuilder.append("\n" + "------------------------------------------------------------------------------------------------" + "\n");

                    }

                    if (thing == "Save") {
                        savedIngredients.add(titleBuilder.toString());
                    }

                }

                else if(queryFinder == "/analyzedInstructions?") {

                    JSONArray reader2 = new JSONArray(jsonData.toString());

                    for (int i = 0; i < reader2.length(); i++) {
                        JSONObject item = reader2.getJSONObject(i);

                        JSONArray thing3 = item.getJSONArray("steps");

                        titleBuilder.append("recipe steps for id: " + id + "\n" + "------------------------------------------------------------------------------------------------" + "\n");

                        for (int j = 0; j < thing3.length(); j++) {
                            JSONObject item2 = thing3.getJSONObject(j);

                            String idnum = item2.getString("step");

                            titleBuilder.append("Step " + (j + 1) + ": ");

                            titleBuilder.append(idnum + "\n" + "------------------------------------------------------------------------------------------------" + "\n");

                        }

                    }

                    if (thing == "Save") {
                        savedData.add(titleBuilder.toString());
                    }
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

            for (int i = 0; i < savedData.size(); i++) {
                Log.i("Thing", savedData.get(i));
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

    private class ResultData {
        String titleStr = "";
    }

    // 482574
    // 723984
    // 121545

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences sp = getSharedPreferences("MyPref2", 0);
        SharedPreferences.Editor editor = sp.edit();

        SharedPreferences sp2 = getSharedPreferences("MyPref3", 0);
        SharedPreferences.Editor editor2 = sp2.edit();

        editor.putInt("arraySize2", savedData.size());
        editor2.putInt("arraySize3", savedIngredients.size());

        for (int i = 0; i < savedData.size(); i++) {
            editor.putString("thing2" + i, savedData.get(i));
        }

        for (int i = 0; i < savedIngredients.size(); i++) {
            editor2.putString("thing3" + i, savedIngredients.get(i));
        }

        editor.commit();
        editor2.commit();
    }


}