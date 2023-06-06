package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class LogInPageActivity  extends AppCompatActivity {
    Timer timer;
    private DataBaseHandler DBManager;
    public Button login_button;
    public EditText edit_ID;
    public EditText edit_password;
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);

        DBManager = new DataBaseHandler(this);

        login_button = (Button) findViewById(R.id.login_button);
        edit_ID = (EditText) findViewById(R.id.login_editTextNumber);
        edit_password = (EditText) findViewById(R.id.login_editTextPassword);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyNotNulls()){
                    DBManager.verifyPassword(edit_ID.getText().toString(), edit_password.getText().toString(), new VerificationBooleanCallback() {
                        @Override
                        public void onVerificationResult(boolean state) {
                            if (state){
                                DBManager.getRecipes(new VerificationBooleanCallback() {
                                    @Override
                                    public void onVerificationResult(boolean state) {
                                        Client.email_client = edit_ID.getText().toString();
                                        Intent intent = new Intent(LogInPageActivity.this, RecipesActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            } else {
                                AlertDialog.Builder errorMessage = new AlertDialog.Builder(v.getContext());
                                errorMessage.setCancelable(true);
                                errorMessage.setMessage("Incorrect email or password.");
                                AlertDialog errorAlert = errorMessage.create();
                                errorAlert.show();
                            }
                        }
                    });

                }
            }
        });
    }

    private boolean verifyNotNulls(){
        boolean status = true;
        AlertDialog.Builder errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);
        if(edit_ID.getText().toString().equals("")){
            Log.e("LOGIN TEST", "FAILED AT ID NULL");
            errorMessage.setMessage("Email Box has to be filled.");
            status = false;
        }else if(edit_password.getText().toString().equals("")){
            Log.e("LOGIN TEST", "FAILED AT PASSWORD NULL");
            errorMessage.setMessage("Password Box has to be filled.");
            status = false;
        } if (!status){
            AlertDialog errorAlert = errorMessage.create();
            errorAlert.show();
        }
        return status;
    }
}

