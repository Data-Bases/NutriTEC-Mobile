package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductsActivity extends AppCompatActivity {
    public Button back_button;
    public Button save_button;
    public EditText recipe_name;
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
        setContentView(R.layout.add_products_activity);
        iniWidgets();
        setClassAdapter();

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        back_button = (Button) findViewById(R.id.back_button);
        save_button = (Button) findViewById(R.id.save_button);

        recipe_name = (EditText) findViewById(R.id.recipe_name_box);

        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductToAdd.products_to_add.clear();
                dataBaseHandler.getRecipes(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        Intent intent = new Intent(AddProductsActivity.this, RecipesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipe_name.getText().toString().equals("") || ProductToAdd.products_to_add.isEmpty()){
                    errorMessage.setMessage("In order to create a recipe you need a name and at least one ingredient.");
                    AlertDialog errorAlert = errorMessage.create();
                    errorAlert.show();
                } else {
                    //ESPACIO PARA HACER EL POST CON LA BASE DE DATOS

                    ProductToAdd.products_to_add.clear();
                    dataBaseHandler.getRecipes(new VerificationBooleanCallback() {
                        @Override
                        public void onVerificationResult(boolean state) {
                            Intent intent = new Intent(AddProductsActivity.this, RecipesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });




    }

    private void setClassAdapter() {
        AddProductsAdapter classAdapter = new AddProductsAdapter(getApplicationContext(), Product.products);
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
