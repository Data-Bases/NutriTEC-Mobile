package com.example.nutritec_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
    public Button logout_button;
    private SQLiteManager sqLiteManager;
    private ListView classListView;
    private AlertDialog.Builder errorMessage;
    int deltaCapacity;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        iniWidgets();
        setClassAdapter();

        sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        errorMessage = new AlertDialog.Builder(this);
        errorMessage.setCancelable(true);

        logout_button = (Button) findViewById(R.id.logout_button);
        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ClassService.classArrayList.get(position).getCapacity() == 0){
                    errorMessage.setMessage("This class isn't allowing more enrollments.");
                } else {
                    deltaCapacity = 0;
                    if(!sqLiteManager.isEnrrolled(Client.ID_client, ClassService.classArrayList.get(position).getID())){
                        errorMessage.setMessage("Are you sure you want to drop from this class?");
                        deltaCapacity = -1;
                    } else {
                        errorMessage.setMessage("Are you sure you want to enroll to this class?");
                        deltaCapacity = 1;
                    }
                    errorMessage.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (deltaCapacity == 1){
                                        sqLiteManager.addClientClass(Client.ID_client, ClassService.classArrayList.get(position).getID());
                                    } else {
                                        sqLiteManager.removeClientClass(Client.ID_client, ClassService.classArrayList.get(position).getID());
                                    }

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
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }

        });


    }

    private void setClassAdapter() {
        ClassAdapter classAdapter = new ClassAdapter(getApplicationContext(), ClassService.classArrayList);
        classListView.setAdapter(classAdapter);
    }

    private void iniWidgets() {
        classListView = findViewById(R.id.classes_listView);
    }

    public void newClass(View view){
        Intent newClassIntent = new Intent(this, LogInActivity.class);
        startActivity(newClassIntent);
    }
}
