package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    public Button logout_button;
    public Button recipes_button;
    public Button daily_button;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        logout_button = (Button) findViewById(R.id.logout_button);
        recipes_button = (Button) findViewById(R.id.recipe_button);
        daily_button = (Button) findViewById(R.id.daily_button);

        recipes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                dataBaseHandler.getRecipes(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent(HomeActivity.this, RecipesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        daily_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MealActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LogInPageActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }

}
