package com.example.samar.backingapp;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivty extends AppCompatActivity {
    private List<Ingredient_Item> Ingredients;
    private List<Step_Item> Steps;
    private String name="";
    private boolean mTwoPane;
    public static final String EXTRA_CURSOR_POSITION ="CursorPosition";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle arguments = getIntent().getExtras();
        Ingredients=arguments.getParcelableArrayList("Ingredients");
        Steps=arguments.getParcelableArrayList("Steps");
        name = arguments.getString("Recipe_name");

        if (findViewById(R.id.Step) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        DetailFragment recipeDetailFragment = new DetailFragment();
        Bundle Arg = new Bundle();
        Arg.putParcelableArrayList("Ingredients", (ArrayList<? extends Parcelable>) Ingredients);
        arguments.putParcelableArrayList("Steps", (ArrayList<? extends Parcelable>) Steps);
        arguments.putString("name",name);
        recipeDetailFragment.setArguments(arguments);
        recipeDetailFragment.isTwoPane(mTwoPane);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.Ingredients, recipeDetailFragment)
                .commit();

    }
    }

