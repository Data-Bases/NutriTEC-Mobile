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

public class AddEditRecipeActivity extends AppCompatActivity {
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
        setContentView(R.layout.add_edit_recipe_activity);
        iniWidgets();
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        back_button = (Button) findViewById(R.id.back_addprod_button);
        save_button = (Button) findViewById(R.id.save_button);

        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductToAdd.products_to_add.clear();
                dataBaseHandler.getRecipe(ProductToAdd.currentRecipe, new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        Intent intent = new Intent(AddEditRecipeActivity.this, EditProductsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductToAdd.products_to_add.clear();
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
                if (!ProductToAdd.products_to_add.isEmpty()){
                    for (ProductToAdd product : ProductToAdd.products_to_add){
                        dataBaseHandler.addProductToRecipe(product.getId(), product.getServings(), new VerificationBooleanCallback() {
                            @Override
                            public void onVerificationResult(boolean state) {

                            }
                        });
                    }
                }
                dataBaseHandler.getRecipe(ProductToAdd.currentRecipe, new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        ProductToAdd.products_to_add.clear();
                        loadingDialog.dismiss();
                        Intent intent = new Intent(AddEditRecipeActivity.this, EditProductsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
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
