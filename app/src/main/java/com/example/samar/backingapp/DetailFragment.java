package com.example.samar.backingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samar on 19/06/2018.
 */

public class DetailFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler {
    private List<Ingredient_Item> Ingredients;
    private List<Step_Item> Steps;
    private String name="";
    private TextView NameTextView;
    private boolean mTwoPane;
    private RecyclerView Ingredient;
    private IngredientsStepsAdapter ingredientsAdapter;
    private RecyclerView StepsRecyclerView;
    private StepsAdapter stepsAdapter;
    private Cursor cursor;
    private Cursor widgetCursor;
    private int positionCursor;
    public static final String EXTRA_CURSOR_POSITION ="CursorPosition";
    private ArrayList<Ingredient_Item> ingredientsList;

    public DetailFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Ingredients = bundle.getParcelableArrayList("Ingredients");
            Steps = bundle.getParcelableArrayList("Steps");
            name=bundle.getString("name");
            NameTextView = (TextView)
                    rootView.findViewById(R.id.name);
            Ingredient = (RecyclerView) rootView.findViewById(R.id.ingredients);
            Ingredient.setLayoutManager(new LinearLayoutManager(getContext()));
            NameTextView.setText(name);
            ingredientsAdapter = new IngredientsStepsAdapter(getContext(), Ingredients);
            Ingredient.setAdapter(ingredientsAdapter);

            StepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.Steps);
            StepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            stepsAdapter = new StepsAdapter(getContext(), this, Steps);
            StepsRecyclerView.setAdapter(stepsAdapter);
        }
         else{
            positionCursor=0;
            try {

                cursor = getActivity().getContentResolver().query(IngredientContract.IngredientEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }


            cursor.moveToPosition(positionCursor);
            int index=cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_NAME);
            int idIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID);
            int ingredientIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_INGREDIENT);
            int quantityIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_QUANTITY);
            int measureIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_MEASURE);
            String name = cursor.getString(index);
            int id = cursor.getInt(idIndex);
            String quantity = cursor.getString(quantityIndex);
            String measure = cursor.getString(measureIndex);
            String ingredient = cursor.getString(ingredientIndex);



            ingredientsList=new ArrayList<>();

            String[] mesureArr = measure.split(",");
            String[] ingredientArr=ingredient.split(",");
            String[] quantityArr=quantity.split(",");


            for(int i=0;i<mesureArr.length;i++)
            {


                ingredientsList.add(i,new Ingredient_Item(quantityArr[i],mesureArr[i],ingredientArr[i]));

            }



            ingredientsAdapter = new IngredientsStepsAdapter(getContext(), ingredientsList);
            Ingredient.setAdapter(ingredientsAdapter);
            NameTextView.setText(name);
        }
        return rootView;
    }

    public void isTwoPane(boolean twoPane) {
        mTwoPane=twoPane;
    }

    @Override
    public void onClick(Step_Item step) {
        if(mTwoPane==true) {
            Toast.makeText(getActivity(),"LANDSCAPE",Toast.LENGTH_LONG);
            StepFragment SF = new StepFragment();
            SF.setStepIndex(step);
            Bundle bundle = new Bundle();
            bundle.putParcelable("StepObject", (Parcelable) step);
            SF.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Step, SF)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(getActivity(), StepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("StepObject", (Parcelable) step);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
