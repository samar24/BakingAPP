package com.example.samar.backingapp;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.samar.backingapp.IngredientContract.BASE_CONTENT_URI;
import static com.example.samar.backingapp.IngredientContract.PATH_INGREDIENTS;

/**
 * Created by Samar on 19/06/2018.
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>  {
    private RecyclerView recyclerView;
    private MainRecipesAdapter adapter;
    private List<Recipe_item> mRecipesData;

    private ProgressDialog progressDialog;
    public MainFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_main
                , container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initCollapsingToolbar(rootView);

        boolean startedLoader=false;
        recyclerView = (RecyclerView)rootView. findViewById(R.id.recycler_view);

        mRecipesData=new ArrayList<>();


        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView)rootView. findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog = new ProgressDialog(getActivity(),R.style.Theme_AppCompat_Dialog);
        progressDialog .setMessage("Loading...");

        progressDialog .setCancelable(false);
        progressDialog .show();
        if(!startedLoader){
            ((AppCompatActivity)getActivity()). getSupportLoaderManager().initLoader(1,null,this);
            startedLoader=true;
        }
        return rootView;
    }
    private void initCollapsingToolbar(View rootView) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }





    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderEx(getActivity());

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        Recipe_item item;
        List<Ingredient_Item> mRecipeIngredients;
        List<Step_Item> mRecipeSteps;

        try {
            JSONArray itemsArray = new JSONArray(data);
            for (int i = 0; i < itemsArray.length(); i++)
            {
                Ingredient_Item Ingr_item;
                Step_Item Ste_Item;
                item = new Recipe_item();
                mRecipeIngredients=new ArrayList<>();
                mRecipeSteps=new ArrayList<>();
                JSONObject obj1 = itemsArray.getJSONObject(i);
                String RecipeId=obj1.getString("id");
                String RecipeName=obj1.getString("name");
                String RecipeServings=obj1.getString("servings");
                String RecipeImage=obj1.getString("image");
                JSONArray IngredientsArray=obj1.getJSONArray("ingredients");
                ContentValues values = new ContentValues();



                for (int j = 0; j < IngredientsArray.length(); j++)
                {
                    Ingr_item=new Ingredient_Item();
                    JSONObject obj2 = IngredientsArray.getJSONObject(j);
                    String quantity=obj2.getString("quantity");
                    String measure=obj2.getString("measure");
                    String ingredient=obj2.getString("ingredient");
                    Ingr_item.setquantity(quantity);
                    Ingr_item.setmeasure(measure);
                    Ingr_item.setingredient(ingredient);
                   values.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_ID, RecipeId);
                    values.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_RECIPE_NAME, RecipeName);
                    values.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_MEASURE, measure);
                    values.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_INGREDIENT, quantity);
                    values.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENTS_QUANTITY, ingredient);
                    getActivity().getContentResolver().insert(IngredientContract.IngredientEntry.CONTENT_URI,
                            values);
                    mRecipeIngredients.add(Ingr_item);
                }
                JSONArray StepsArray=obj1.getJSONArray("steps");
                for (int k = 0; k < StepsArray.length(); k++)
                {
                    Ste_Item=new Step_Item();
                    JSONObject obj3 = StepsArray.getJSONObject(k);
                    String ID=obj3.getString("id");
                    String shortDescription=obj3.getString("shortDescription");
                    String description=obj3.getString("description");
                    String videoURL=obj3.getString("videoURL");
                    String thumbnailURL=obj3.getString("thumbnailURL");
                    Ste_Item.setID(ID);
                    Ste_Item.setshortDescription(shortDescription);
                    Ste_Item.setdescription(description);
                    Ste_Item.setvideoURL(videoURL);
                    Ste_Item.setthumbnailURL(thumbnailURL);
                    mRecipeSteps.add(Ste_Item);
                }
                item.SetRecipeId(RecipeId);
                item.SetImage(RecipeImage);
                item.SetServings(RecipeServings);
                item.setName(RecipeName);
                item.SetIngredients(mRecipeIngredients);
                item.Setsteps(mRecipeSteps);
                mRecipesData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       // if(progressDialog!=null)
            progressDialog.dismiss();
        adapter = new MainRecipesAdapter(getActivity(), mRecipesData);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }





    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public static class AsyncTaskLoaderEx<String> extends AsyncTaskLoader<String> {
        private String mData;
        public boolean hasResult = false;
        String json;
        UserFunctions userFunctions=new UserFunctions();

        public AsyncTaskLoaderEx(final Context context) {
            super(context);
            onContentChanged();
        }

        @Override
        public String loadInBackground() {

            try {
                json = (String) userFunctions.GetRecipes();





            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onStartLoading() {
            if (takeContentChanged())
                forceLoad();
            else if (hasResult)
                deliverResult(mData);
        }

        @Override
        public void deliverResult(final String data) {
            mData = data;
            hasResult = true;
            super.deliverResult(data);
        }

        @Override
        protected void onReset() {
            super.onReset();
            onStopLoading();
            if (hasResult) {
                mData = null;
                hasResult = false;
            }
        }
    }
}
