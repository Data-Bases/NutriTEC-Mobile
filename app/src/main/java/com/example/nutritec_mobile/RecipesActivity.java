package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipesActivity extends AppCompatActivity {
    public Button back_button;
    public Button add_button;
    public ListView classListView;
    private AlertDialog.Builder errorMessage;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);
        iniWidgets();
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        AlertDialog loadingDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.TransparentAlertDialog);
        builder.setView(R.layout.dialog_loading);
        builder.setCancelable(false);
        loadingDialog = builder.create();

        back_button = (Button) findViewById(R.id.back_recipe_button);
        add_button = (Button) findViewById(R.id.add_recipe_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                dataBaseHandler.getProducts(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(RecipesActivity.this, AddProductsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }

    private void setClassAdapter(Context aux) {
        RecipeAdapter classAdapter = new RecipeAdapter(getApplicationContext(), aux, Recipe.recipes);
        classListView.setAdapter(classAdapter);
    }

    private void iniWidgets() {
        classListView = findViewById(R.id.classes_listView);
    }

    public void newClass(View view){
        Intent newClassIntent = new Intent(this, LogInPageActivity.class);
        startActivity(newClassIntent);
    }
}
