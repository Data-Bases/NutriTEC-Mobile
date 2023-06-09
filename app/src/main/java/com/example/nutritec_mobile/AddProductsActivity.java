package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        back_button = (Button) findViewById(R.id.back_addprod_button);
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
                            Product product_to_check = new Product();
                            try{
                                product_to_check = (Product) classListView.getAdapter().getItem(i);
                            } catch (Exception e){
                            }
                            ProductToAdd new_product = new ProductToAdd(product_to_check.getId(),product_to_check.getName(), Double.parseDouble(servings.getText().toString()));
                            ProductToAdd.products_to_add.add(new_product);
                        }
                    } catch (Exception e){

                    }
                }
                if(recipe_name.getText().toString().equals("") || ProductToAdd.products_to_add.isEmpty()){
                    loadingDialog.dismiss();
                    errorMessage.setMessage("In order to create a recipe you need a name and at least one ingredient.");
                    AlertDialog errorAlert = errorMessage.create();
                    errorAlert.show();
                } else {
                    dataBaseHandler.addRecipe(recipe_name.getText().toString(), new VerificationBooleanCallback() {
                        @Override
                        public void onVerificationResult(boolean state) {
                            ProductToAdd.products_to_add.clear();
                            dataBaseHandler.getRecipes(new VerificationBooleanCallback() {
                                @Override
                                public void onVerificationResult(boolean state) {
                                    loadingDialog.dismiss();
                                    Intent intent = new Intent(AddProductsActivity.this, RecipesActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });

                    //ProductToAdd.products_to_add.clear();
                }
            }
        });




    }

    private void setClassAdapter(Context aux) {
        AddProductsAdapter classAdapter = new AddProductsAdapter(getApplicationContext(), aux,Product.products);
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
