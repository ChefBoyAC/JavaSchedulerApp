package com.example.javaschedulerapp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.model.UserData;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final Context context;
    private final ArrayList<UserData> userList;

    public UserAdapter(Context context, ArrayList<UserData> userList) {
        this.context = context;
        this.userList = userList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView userSchedule;
        TextView userInstructor;

        TextView userLocation;

        //get the linear layout of the row
        LinearLayout listLayoutRow;

        public UserViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.mTitle);
            userSchedule = v.findViewById(R.id.subTitle);
            userInstructor = v.findViewById(R.id.anotherSubTitle);
            userLocation = v.findViewById(R.id.anotherAnotherSubTitle);
            listLayoutRow = v.findViewById(R.id.listLayout);
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        UserData newList = userList.get(position);
        holder.name.setText(newList.getUsername());
        holder.userSchedule.setText(newList.getUserSchedule());
        holder.userInstructor.setText(newList.getUserInstructor());
        holder.userLocation.setText(newList.getUserLocation());

        //this is where we update the code
        holder.listLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a new dialog with the parameters of the courses
                /**AlertDialog dialog = new AlertDialog.Builder(context);
                dialog.setContentView(R.layout.add_item);

                EditText courseName = dialog.findViewById(R.id.courseName);
                EditText dateAndTimeOfClass = dialog.findViewById(R.id.dateAndTimeOfClass);
                EditText instructor = dialog.findViewById(R.id.instructor);
                EditText location = dialog.findViewById(R.id.location);
                //include the cancel and update button

                //change the current code in the system

                //show the dialog to the screen
                dialog.show();
                 **/
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
