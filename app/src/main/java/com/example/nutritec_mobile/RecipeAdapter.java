package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe>{
    public static boolean firstRefresh = false;
    private DataBaseHandler dataBaseHandler;
    private AlertDialog.Builder errorMessage;
    private AlertDialog loadingDialog;
    private AlertDialog.Builder builder;


    public RecipeAdapter(Context context, Context aux, List<Recipe> classes) {
        super(context, 0, classes);
        dataBaseHandler = new DataBaseHandler(context);
        errorMessage = new AlertDialog.Builder(aux);
        errorMessage.setCancelable(true);
        builder = new AlertDialog.Builder(aux, R.style.TransparentAlertDialog);
        builder.setView(R.layout.dialog_loading);
        builder.setCancelable(false);
        loadingDialog = builder.create();
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
        Button delete = convertView.findViewById(R.id.delete_button);
        Button edit = convertView.findViewById(R.id.edit_button);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                ProductToAdd.currentRecipe = recipe.getId();
                dataBaseHandler.getRecipe(recipe.getId(), new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(loadingDialog.getContext(), EditProductsActivity.class);
                        loadingDialog.getContext().startActivity(intent);
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setMessage("Are you sure you want delete this recipe?");
                errorMessage.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                loadingDialog.show();
                                dataBaseHandler.deleteRecipe(recipe.getId(), new VerificationBooleanCallback() {
                                    @Override
                                    public void onVerificationResult(boolean state) {
                                        remove(recipe);
                                        notifyDataSetChanged();
                                        loadingDialog.dismiss();
                                    }
                                });
                                dialog.cancel();
                            }
                        });

                errorMessage.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertMessage = errorMessage.create();
                alertMessage.show();
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().contains("\n")){
                    name.setText(recipe.getName());
                } else {
                    loadingDialog.show();
                    dataBaseHandler.getRecipeByID(recipe.getId(), 1.0, new VerificationStringCallback() {
                        @Override
                        public void onVerificationResult(String response) {
                            name.setText(response);
                            loadingDialog.dismiss();
                        }
                    });

                }
            }
        });

        if (name.getText().toString().split("\n").length == 1){
            name.setText(recipe.getName());
        }
        convertView.setEnabled(true);
        this.notifyDataSetChanged();
        return convertView;
    }
}
