package com.example.samar.backingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samar on 19/06/2018.
 */

public class IngredientsStepsAdapter extends RecyclerView.Adapter<IngredientsStepsAdapter.IngredientsStepsAdapterViewHolder>{

    int Position ;
    private List<Ingredient_Item> Ingredients;
    private Context mContex;


    public IngredientsStepsAdapter(Context context, List<Ingredient_Item> ArrayList) {
        this.mContex=context;
        this.Ingredients=ArrayList;
    }

    public class IngredientsStepsAdapterViewHolder extends RecyclerView.ViewHolder   {


        public TextView mDetailTextView;


        public IngredientsStepsAdapterViewHolder(View view) {
            super(view);
            mDetailTextView = (TextView) view.findViewById(R.id.recipe_Ingredient);
        }


    }

    @Override
    public IngredientsStepsAdapter.IngredientsStepsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingr_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IngredientsStepsAdapter.IngredientsStepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsStepsAdapterViewHolder holder, int position) {
        Ingredient_Item ingredient=Ingredients.get(position);
        holder.mDetailTextView.setText(ingredient.getquantity()+" "+ingredient.getmeasure()+" of "+ingredient.getingredient());

    }




    @Override
    public int getItemCount() {
        if (null == Ingredients)
            return 0;
        return Ingredients.size();
    }



}