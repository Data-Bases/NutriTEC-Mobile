package com.example.nutritec_mobile;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe>{

    private DataBaseHandler dataBaseHandler;


    public RecipeAdapter(Context context, List<Recipe> classes) {
        super(context, 0, classes);
        dataBaseHandler = new DataBaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Recipe recipe = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_cell, parent, false);
        }
        TextView name = convertView.findViewById(R.id.cellName);

        Button info = convertView.findViewById(R.id.info_button);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().contains("\n")){
                    name.setText(recipe.getName());
                } else {
                    dataBaseHandler.getRecipeByID(recipe.getId(), 1, new VerificationStringCallback() {
                        @Override
                        public void onVerificationResult(String response) {
                            name.setText(response);
                        }
                    });

                }
            }
        });


        name.setText(recipe.getName());
        return convertView;
    }
}
