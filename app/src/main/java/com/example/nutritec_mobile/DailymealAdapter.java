package com.example.nutritec_mobile;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DailymealAdapter extends ArrayAdapter<DailyMeal>{
    public static boolean firstRefresh = false;

    private DataBaseHandler dataBaseHandler;
    private AlertDialog.Builder errorMessage;
    private AlertDialog loadingDialog;
    private AlertDialog.Builder builder;


    public DailymealAdapter(Context context, Context aux, List<DailyMeal> classes) {
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

        DailyMeal dailyMeal = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dailymeal_cell, parent, false);
        }
        TextView name = convertView.findViewById(R.id.cellName);

        Button info = convertView.findViewById(R.id.info_button);
        DailymealAdapter temp = this;

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().contains("\n")){
                    name.setText(dailyMeal.getName());
                } else {
                    if (dailyMeal.getType().equals("R")){
                        loadingDialog.show();
                        dataBaseHandler.getRecipeByID(dailyMeal.getId(), dailyMeal.getServings(), new VerificationStringCallback() {
                            @Override
                            public void onVerificationResult(String response) {
                                name.setText(response);
                                loadingDialog.dismiss();
                            }
                        });
                    } else {
                        loadingDialog.show();
                        dataBaseHandler.getProductByID(dailyMeal.getId(), dailyMeal.getServings(), new VerificationStringCallback() {
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

        if (name.getText().toString().split("\n").length == 1){
            name.setText(dailyMeal.getName());
        }
        this.notifyDataSetChanged();
        return convertView;
    }
}
