package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddDailyActivity extends AppCompatActivity {
    public Button back_button;
    public Button save_button;
    private ListView classListView;
    private AlertDialog.Builder errorMessage;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_daily_activity);
        iniWidgets();
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        back_button = (Button) findViewById(R.id.back_adddaily_button);
        save_button = (Button) findViewById(R.id.save_button);

        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataBaseHandler.getDaily(getMeal(),new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        Intent intent = new Intent(AddDailyActivity.this, DailymealActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                for (int i = 0; i < classListView.getAdapter().getCount(); i++){
                    try {
                        View desiredView = classListView.getChildAt(i);
                        Button addButton = desiredView.findViewById(R.id.add_button);
                        TextView servings = desiredView.findViewById(R.id.portion);
                        TextView name = desiredView.findViewById(R.id.cellName);
                        if (addButton.getText().toString().equals("-")) {
                            for (DailyMeal product_to_check : DailyMeal.recipes_products) {
                                if (product_to_check.getName() == name.getText().toString().split("\n")[0]) {
                                    DailyMeal product;
                                    product = product_to_check;
                                    product.setServings(Double.parseDouble(servings.getText().toString()));
                                    DailyMeal.add_recipes_products.add(product);
                                }
                            }
                        }
                    } catch (Exception e){

                    }
                }
                if(DailyMeal.add_recipes_products.isEmpty()){
                    loadingDialog.dismiss();
                    errorMessage.setMessage("In order make an entry you need at least one product or recipe.");
                    AlertDialog errorAlert = errorMessage.create();
                    errorAlert.show();
                } else {
                    dataBaseHandler.addDaily(new VerificationBooleanCallback() {
                        @Override
                        public void onVerificationResult(boolean state) {
                            DailyMeal.add_recipes_products.clear();
                            dataBaseHandler.getDaily(getMeal(),new VerificationBooleanCallback() {
                                @Override
                                public void onVerificationResult(boolean state) {
                                    loadingDialog.dismiss();
                                    Intent intent = new Intent(AddDailyActivity.this, DailymealActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        });




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

    private void setClassAdapter(Context aux) {
        AddDailyAdapter classAdapter = new AddDailyAdapter(getApplicationContext(), aux, DailyMeal.recipes_products);
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
