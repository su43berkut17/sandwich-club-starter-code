package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private String name="";
    private String alsoNamed="";
    private String origin="";
    private String description="";
    private String imageURL="";
    private String[] ingredients;

    private static Sandwich sandwichRet=new Sandwich();


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //check the content of the json
        Log.i("TAG",json);

        //create the json object
        JSONObject fullContentObj=new JSONObject(json);
        JSONObject nameObject=fullContentObj.getJSONObject("name");

        //assign vars to sandwich object
        sandwichRet.setMainName(nameObject.getString("mainName"));

        //string list of names
       JSONArray knownAs=nameObject.getJSONArray("alsoKnownAs");
       List<String> listKnown=new ArrayList<>();

       for (int i=0;i<knownAs.length();i++){
           listKnown.add(knownAs.getString(i));
       }

        sandwichRet.setAlsoKnownAs(listKnown);

       //string place of origin
        sandwichRet.setPlaceOfOrigin(fullContentObj.getString("placeOfOrigin"));

        //array ingredients
        JSONArray ingredients=fullContentObj.getJSONArray("ingredients");
        List<String> listIngredients=new ArrayList<>();

        for (int i=0;i<ingredients.length();i++){
            listIngredients.add(ingredients.getString(i));
        }

        sandwichRet.setIngredients(listIngredients);

        //string description
        sandwichRet.setDescription(fullContentObj.getString("description"));

        //image
        sandwichRet.setImage(fullContentObj.getString("image"));


        return sandwichRet;
    }
}
