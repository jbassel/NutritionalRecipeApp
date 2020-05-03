package com.jacobbassel.nutritionalrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    }

    @Override
    public void onClick(View v) {

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
    }



}
