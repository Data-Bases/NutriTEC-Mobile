package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProductsActivity extends AppCompatActivity {

    public Button add_button;

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
        setContentView(R.layout.edit_products_activity);
        iniWidgets();
        setClassAdapter(this);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        save_button = (Button) findViewById(R.id.save_button);

        add_button = (Button) findViewById(R.id.add_product_button);


        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
                        Intent intent = new Intent(EditProductsActivity.this, AddEditRecipeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductToAdd.products_to_edit_after.clear();
                AlertDialog loadingDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.TransparentAlertDialog);
                builder.setView(R.layout.dialog_loading);
                builder.setCancelable(false);
                loadingDialog = builder.create();
                loadingDialog.show();
                for (int i = 0; i < classListView.getAdapter().getCount(); i++){
                    try {
                        View desiredView = classListView.getChildAt(i);
                        TextView servings = desiredView.findViewById(R.id.portion);
                        ProductToAdd product_to_check = new ProductToAdd();
                        try{
                            product_to_check = (ProductToAdd) classListView.getAdapter().getItem(i);
                        } catch (Exception e){
                           Log.e("ERROR CASTING TO PRODUCT ADD", "OBJECT CAN'T BE CASTED");
                        }
                        ProductToAdd new_product = new ProductToAdd(product_to_check.getId(),product_to_check.getName(), Double.parseDouble(servings.getText().toString()));
                        ProductToAdd.products_to_edit_after.add(new_product);

                    } catch (Exception e){
                        Log.e("ERROR CASTING TO PRODUCT ADD", e.getMessage());

                    }
                }
                if (!ProductToAdd.products_to_edit_after.isEmpty()){
                    for (ProductToAdd product : ProductToAdd.products_to_edit_after){
                        Log.i("ID",Integer.toString(product.getId()));
                        Log.i("SERVINGS",Double.toString(product.getServings()));
                        dataBaseHandler.editProductServing(product.getId(), product.getServings(), new VerificationBooleanCallback() {
                            @Override
                            public void onVerificationResult(boolean state) {

                            }
                        });
                    }
                }
                dataBaseHandler.getRecipes(new VerificationBooleanCallback() {
                    @Override
                    public void onVerificationResult(boolean state) {
                        ProductToAdd.products_to_edit_after.clear();
                        loadingDialog.dismiss();
                        Intent intent = new Intent(EditProductsActivity.this, RecipesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    private void setClassAdapter(Context aux) {
        EditProductsAdapter classAdapter = new EditProductsAdapter(getApplicationContext(), aux,ProductToAdd.products_to_edit);
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
