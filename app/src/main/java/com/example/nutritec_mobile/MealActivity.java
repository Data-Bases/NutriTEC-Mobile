package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {
    public Button back_button;
    public Button breakfast_button;
    public Button lunch_button;
    public Button dinner_button;
    public Button snacks_button;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_activity);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        back_button = (Button) findViewById(R.id.back_meal_button);
        breakfast_button = (Button) findViewById(R.id.breakfast_button);
        lunch_button = (Button) findViewById(R.id.lunch_button);
        dinner_button = (Button) findViewById(R.id.dinner_button);
        snacks_button = (Button) findViewById(R.id.snacks_button);

        breakfast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                DailyMeal.currentDaily = "Breakfast";
                dataBaseHandler.getDaily(0,new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(MealActivity.this, DailymealActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        lunch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                DailyMeal.currentDaily = "Lunch";
                dataBaseHandler.getDaily(1,new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(MealActivity.this, DailymealActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        dinner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                DailyMeal.currentDaily = "Dinner";
                dataBaseHandler.getDaily(2,new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(MealActivity.this, DailymealActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        snacks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                DailyMeal.currentDaily = "Snack";
                dataBaseHandler.getDaily(3,new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(MealActivity.this, DailymealActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }

}
