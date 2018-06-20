package com.example.samar.backingapp;

import java.util.List;

/**
 * Created by Samar on 15/05/2018.
 */

public class Recipe_item {
    private String name;
    private String ID;
    private String servings;
    private String Image;
    private List <Ingredient_Item> ingredients;
    private List <Step_Item> steps;

    public Recipe_item() {
    }

    public Recipe_item(String name, String ID, String servings,String Image) {
        this.name = name;
        this.ID = ID;
        this.servings = servings;
        this.Image = Image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void SetRecipeId(String ID) {
        this.ID = ID;
    }

    public String getRecipe_ID() {
        return ID;
    }
    public String GetServings() {
        return servings;
    }
    public void SetServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return Image;
    }
    public void SetImage(String Image) {
        this.Image = Image;
    }

    public void SetIngredients(List<Ingredient_Item> ingredients) {
        this.ingredients = ingredients;
    }
    public List<Ingredient_Item> getingredients() {
        return ingredients;
    }
    public void Setsteps(List<Step_Item> steps) {
        this.steps = steps;
    }
    public List<Step_Item> getsteps() {
        return steps;
    }


}