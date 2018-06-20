package com.example.samar.backingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samar on 15/05/2018.
 */

public class MainRecipesAdapter extends RecyclerView.Adapter<MainRecipesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe_item> RecipesList;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title;
        public ImageView thumbnail, overflow;
        public View container;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            container = view;
        }
    }


    public MainRecipesAdapter(Context mContext, List<Recipe_item> RecipesList) {
        this.mContext = mContext;
        this.RecipesList = RecipesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recyclerview_screen_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,  int position) {
         final Recipe_item Recipe = RecipesList.get(position);
        holder.title.setText(Recipe.getName());

        Glide.with(mContext).load(Recipe.getImage()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivty.class);
                intent.putExtra("Recipe_name",Recipe.getName());
                intent .putParcelableArrayListExtra("Ingredients", (ArrayList<? extends Parcelable>) Recipe.getingredients());
                intent .putParcelableArrayListExtra("Steps", (ArrayList<? extends Parcelable>) Recipe.getsteps());
                mContext.startActivity(intent);
            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_recipe, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return RecipesList.size();
    }
}