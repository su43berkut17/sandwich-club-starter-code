package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //ui
    private TextView tv_name;
    private TextView tv_knownAs;
    private TextView tv_origin;
    private TextView tv_ingredients;
    private TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        //initialize the tvs
        tv_name=(TextView) findViewById(R.id.origin_tv);
        tv_knownAs=(TextView) findViewById(R.id.also_known_tv);
        tv_origin=(TextView) findViewById(R.id.origin_tv);
        tv_ingredients=(TextView) findViewById(R.id.ingredients_tv);
        tv_description=(TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }catch (JSONException e){
            //Log.e("EXEC",getSta);
        }



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Toast.makeText(this, sandwich.getMainName(), Toast.LENGTH_SHORT).show();
        tv_name.setText(sandwich.getMainName());

        //cycle through known as
        String st_knownAs="";
        StringBuilder st_Builder=new StringBuilder();
        List<String> arrayKnown;
        arrayKnown=sandwich.getAlsoKnownAs();

        if (arrayKnown.size()==0){
            tv_knownAs.setText("N/A");
        }else{
           //cycle
           for (int i=0;i<arrayKnown.size();i++){
               if (i<arrayKnown.size()-1) {
                   st_Builder.append(arrayKnown.get(i) + ", ");
               }else{
                   st_Builder.append(arrayKnown.get(i));
               }
           }
           st_knownAs=st_Builder.toString();
            tv_knownAs.setText(st_knownAs);
        }

        //place of origin
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            tv_origin.setText("N/A");
        }else {
            tv_origin.setText(sandwich.getPlaceOfOrigin());
        }

        //ingredients
        String st_ingredients;
        StringBuilder stb_ingredients=new StringBuilder();
        List<String> listIngredients;
        listIngredients=sandwich.getIngredients();

        if (listIngredients.size()==0){
            tv_ingredients.setText("N/A");
        }else{
            //cycle
            for (int i=0;i<listIngredients.size();i++){
                if (i<listIngredients.size()-1) {
                    stb_ingredients.append("- "+listIngredients.get(i) + "\n");
                }else{
                    stb_ingredients.append("- "+listIngredients.get(i));
                }
            }
            st_ingredients=stb_ingredients.toString();
            tv_ingredients.setText(st_ingredients);
        }

        //description
        tv_description.setText(sandwich.getDescription());

    }
}
