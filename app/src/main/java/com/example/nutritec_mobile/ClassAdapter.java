package com.example.nutritec_mobile;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<ClassService>{
    SQLiteManager sqLiteManager;
    public ClassAdapter(Context context, List<ClassService> classes) {
        super(context, 0, classes);
        sqLiteManager = SQLiteManager.instanceOfDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ClassService classService = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_cell, parent, false);
        }
        TextView name = convertView.findViewById(R.id.cellName);
        TextView description = convertView.findViewById(R.id.cellDescription);
        TextView branch = convertView.findViewById(R.id.cellBranch);
        TextView instructor = convertView.findViewById(R.id.cellInstructor);
        TextView date = convertView.findViewById(R.id.cellDate);
        TextView starTime = convertView.findViewById(R.id.cellStartTime);
        TextView endTime = convertView.findViewById(R.id.cellEndTime);
        TextView availability = convertView.findViewById(R.id.cellAvailability);

        name.setText("Name: "+classService.getName());
        description.setText("Description: "+classService.getDescription());
        branch.setText("Branch: "+classService.getBranch());
        instructor.setText("Instructor: "+classService.getInstructor());
        date.setText("Date: "+classService.getDate());
        starTime.setText("Start Time: "+classService.getStartTime().split(" ")[1]);
        endTime.setText("End Time: "+classService.getEndTime().split(" ")[1]);

        if (!sqLiteManager.isEnrrolled(Client.ID_client, classService.getID())){
            availability.setText("Enrolled");
            availability.setTextColor(getContext().getResources().getColor(R.color.palette_green));
        } else {
            if (classService.getCapacity() != 0) {
                availability.setText("Available");
                availability.setTextColor(getContext().getResources().getColor(R.color.palette_blue));
            } else {
                availability.setText("Not Available");
                availability.setTextColor(getContext().getResources().getColor(R.color.palette_red));
            }
        }

        return convertView;
    }
}
