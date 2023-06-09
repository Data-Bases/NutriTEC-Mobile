package com.example.nutritec_mobile;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class EditProductsAdapter extends ArrayAdapter<ProductToAdd>{
    public static boolean firstRefresh = false;
    private DataBaseHandler dataBaseHandler;
    private AlertDialog.Builder errorMessage;
    private AlertDialog loadingDialog;
    private AlertDialog.Builder builder;

    public EditProductsAdapter(Context context, Context aux, List<ProductToAdd> classes) {
        super(context, 0, classes);
        dataBaseHandler = new DataBaseHandler(context);
        errorMessage = new AlertDialog.Builder(aux);
        errorMessage.setCancelable(true);
        builder = new AlertDialog.Builder(aux, R.style.TransparentAlertDialog);
        builder.setView(R.layout.dialog_loading);
        builder.setCancelable(false);
        loadingDialog = builder.create();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductToAdd product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_product_cell, parent, false);
        }

        TextView name = convertView.findViewById(R.id.cellName);
        Button delete = convertView.findViewById(R.id.delete_edit_button);
        EditText servings = convertView.findViewById(R.id.portion);
        Button info = convertView.findViewById(R.id.info_button);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().contains("\n")){
                    name.setText(product.getName());
                } else {
                    if (servings.getText().toString().equals("")){
                        loadingDialog.show();
                        dataBaseHandler.getProductByID(product.getId(), 1.0, new VerificationStringCallback() {
                            @Override
                            public void onVerificationResult(String response) {
                                name.setText(response);
                                loadingDialog.dismiss();
                            }
                        });
                    } else {
                        loadingDialog.show();
                        dataBaseHandler.getProductByID(product.getId(), Double.parseDouble(servings.getText().toString()), new VerificationStringCallback() {
                            @Override
                            public void onVerificationResult(String response) {
                                name.setText(response);
                                loadingDialog.dismiss();
                            }
                        });
                    }

                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setMessage("Are you sure you want delete this product?");
                errorMessage.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                loadingDialog.show();
                                dataBaseHandler.deleteProductFromRecipe(product.getId(), new VerificationBooleanCallback() {
                                    @Override
                                    public void onVerificationResult(boolean state) {
                                        remove(product);
                                        notifyDataSetChanged();
                                        loadingDialog.dismiss();
                                    }
                                });
                                dialog.cancel();
                            }
                        });

                errorMessage.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertMessage = errorMessage.create();
                alertMessage.show();
            }
        });

        if (servings.getText().toString().equals("")){
            servings.setText(Double.toString(product.getServings()));
        }
        if (name.getText().toString().split("\n").length == 1){
            name.setText(product.getName());
        }
        this.notifyDataSetChanged();
        return convertView;
    }
}
