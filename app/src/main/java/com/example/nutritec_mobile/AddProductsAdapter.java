package com.example.nutritec_mobile;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AddProductsAdapter extends ArrayAdapter<Product>{
    private boolean firstRefresh = false;
    private DataBaseHandler dataBaseHandler;

    public AddProductsAdapter(Context context, List<Product> classes) {
        super(context, 0, classes);
        dataBaseHandler = new DataBaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_product_cell, parent, false);
        }

        TextView name = convertView.findViewById(R.id.cellName);
        Button add = convertView.findViewById(R.id.add_button);
        EditText servings = convertView.findViewById(R.id.portion);
        Button info = convertView.findViewById(R.id.info_button);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().contains("\n")){
                    name.setText(product.getName());
                } else {
                    if (servings.getText().toString().equals("")){
                        dataBaseHandler.getProductByID(product.getId(), 1, new VerificationStringCallback() {
                            @Override
                            public void onVerificationResult(String response) {
                                name.setText(response);
                            }
                        });
                    } else {
                        dataBaseHandler.getProductByID(product.getId(), Integer.parseInt(servings.getText().toString()), new VerificationStringCallback() {
                            @Override
                            public void onVerificationResult(String response) {
                                name.setText(response);
                            }
                        });
                    }

                }
            }
        });

        if(add.getText().equals("+")){
            servings.setEnabled(false);
        } else {
            if(servings.getText().toString().equals("") || servings.getText().toString().equals("0")){
                servings.setText("1");
            }
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TO ADD LIST", ProductToAdd.products_to_add.toString());
                if(servings.isEnabled() == false){
                    add.setText("-");
                    servings.setEnabled(true);
                    servings.setText("1");
                    ProductToAdd productToAdd = new ProductToAdd(product.getId(), Integer.parseInt(servings.getText().toString()));
                    ProductToAdd.products_to_add.add(productToAdd);
                } else {
                    add.setText("+");
                    servings.setEnabled(false);
                    servings.setText("");
                    for (int i = 0; i < ProductToAdd.products_to_add.size(); i++){
                        if (ProductToAdd.products_to_add.get(i).getId() == product.getId()){
                            ProductToAdd.products_to_add.remove(i);
                        }
                    }

                }
            }
        });

        name.setText(product.getName());
        return convertView;
    }
}
