package com.example.samar.backingapp;
/**
 * Created by Samar on 12/18/2015.
 */
import java.io.IOException;
import java.util.HashMap;

public class UserFunctions {
     
   
    private static String Recipes ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public UserFunctions(){
    }
    public String GetRecipes() throws IOException{
   	 String json;
   	   JSONParser jsonParser = new JSONParser();

		HashMap<String, String>  params = new HashMap<String, String>();
   	  json = jsonParser.makeHttpRequest(Recipes, "GET", params);
   	 return json;
   	
   }

}
