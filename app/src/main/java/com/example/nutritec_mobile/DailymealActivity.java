package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class DailymealActivity extends AppCompatActivity {
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
        setContentView(R.layout.dailymeal_activity);
        iniWidgets();
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

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
                dataBaseHandler.getProductsAndRecipes(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(DailymealActivity.this, AddDailyActivity.class);
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
                Intent intent = new Intent(DailymealActivity.this, MealActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void setClassAdapter(Context aux) {
        if (DailyMeal.currentDaily.equals("Breakfast")){
            DailymealAdapter classAdapter = new DailymealAdapter(getApplicationContext(), aux, DailyMeal.breakfast);
            classListView.setAdapter(classAdapter);
        } else if (DailyMeal.currentDaily.equals("Lunch")){
            DailymealAdapter classAdapter = new DailymealAdapter(getApplicationContext(), aux, DailyMeal.lunch);
            classListView.setAdapter(classAdapter);
        } else if (DailyMeal.currentDaily.equals("Dinner")){
            DailymealAdapter classAdapter = new DailymealAdapter(getApplicationContext(), aux, DailyMeal.dinner);
            classListView.setAdapter(classAdapter);
        } else if (DailyMeal.currentDaily.equals("Snack")){
            DailymealAdapter classAdapter = new DailymealAdapter(getApplicationContext(), aux, DailyMeal.snacks);
            classListView.setAdapter(classAdapter);
        }
    }

    private void iniWidgets() {
        classListView = findViewById(R.id.classes_listView);
    }

    public void newClass(View view){
        Intent newClassIntent = new Intent(this, LogInPageActivity.class);
        startActivity(newClassIntent);
    }

    private int getMeal() {
        if (DailyMeal.currentDaily.equals("Breakfast")){
            return 0;
        } else if (DailyMeal.currentDaily.equals("Lunch")){
            return 1;
        } else if (DailyMeal.currentDaily.equals("Dinner")){
            return 2;
        } else if (DailyMeal.currentDaily.equals("Snack")){
            return 3;
        } else {
            return 0;
        }

    }
}
