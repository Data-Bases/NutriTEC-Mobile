package com.example.nutritec_mobile;
import android.app.AlertDialog;
import android.content.Context;
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

public class AddDailyAdapter extends ArrayAdapter<DailyMeal>{
    public static boolean firstRefresh = false;
    private DataBaseHandler dataBaseHandler;
    private AlertDialog loadingDialog;
    private AlertDialog.Builder builder;

    public AddDailyAdapter(Context context,Context aux, List<DailyMeal> classes) {
        super(context, 0, classes);
        dataBaseHandler = new DataBaseHandler(context);
        builder = new AlertDialog.Builder(aux, R.style.TransparentAlertDialog);
        builder.setView(R.layout.dialog_loading);
        builder.setCancelable(false);
        loadingDialog = builder.create();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DailyMeal product = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_daily_cell, parent, false);
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
                        if (product.getType().equals("P")){
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
                            dataBaseHandler.getRecipeByID(product.getId(), 1.0, new VerificationStringCallback() {
                                @Override
                                public void onVerificationResult(String response) {
                                    name.setText(response);
                                    loadingDialog.dismiss();
                                }
                            });
                        }
                    } else {
                        if (product.getType().equals("P")){
                            loadingDialog.show();
                            dataBaseHandler.getProductByID(product.getId(), Double.parseDouble(servings.getText().toString()), new VerificationStringCallback() {
                                @Override
                                public void onVerificationResult(String response) {
                                    name.setText(response);
                                    loadingDialog.dismiss();
                                }
                            });
                        } else {
                            loadingDialog.show();
                            dataBaseHandler.getRecipeByID(product.getId(), Double.parseDouble(servings.getText().toString()), new VerificationStringCallback() {
                                @Override
                                public void onVerificationResult(String response) {
                                    name.setText(response);
                                    loadingDialog.dismiss();
                                }
                            });
                        }

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
                if(servings.isEnabled() == false){
                    add.setText("-");
                    servings.setEnabled(true);
                    servings.setText("1");
                } else {
                    add.setText("+");
                    servings.setEnabled(false);
                    servings.setText("");

                }
            }
        });

        if (name.getText().toString().split("\n").length == 1){
            name.setText(product.getName());
        }
        this.notifyDataSetChanged();
        return convertView;
    }

}
