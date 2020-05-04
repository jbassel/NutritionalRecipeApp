package com.jacobbassel.nutritionalrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        Button ingredient = findViewById(R.id.ingredient_button);
        ingredient.setOnClickListener(this);

        Button saved = findViewById(R.id.saved_button);
        saved.setOnClickListener(this);

        Button recipe = findViewById(R.id.recipe_button);
        recipe.setOnClickListener(this);

        Button about1 = findViewById(R.id.button4);
        about1.setOnClickListener(this);

        Button about2 = findViewById(R.id.button5);
        about2.setOnClickListener(this);

        Button about3 = findViewById(R.id.button6);
        about3.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        TextView text = findViewById(R.id.textView);

        if (v.getId() == R.id.ingredient_button) {
            Intent intent = new Intent(this, IngredientsActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.recipe_button){
            Intent intent = new Intent(this, RecipesActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.saved_button) {
            Intent intent = new Intent(this, SavedActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.button4) {
           text.setText("This activity will allow you to search for a recipe based on ingredient.  If you see the ID you like, input it and click the save button.");
        }

        if (v.getId() == R.id.button5) {
            text.setText("This activity will allow you to search for the ingredient or recipe steps based on ID.  If you like the recipe or steps set press save after you search them.  Remember, you can only have one save at a time.");
        }

        if (v.getId() == R.id.button6) {
            text.setText("This activity will allow you to view your saved data.");
        }

    }



}
