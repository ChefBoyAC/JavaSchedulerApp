package com.example.javaschedulerapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        public UserViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.mTitle);
            userSchedule = v.findViewById(R.id.subTitle);
            userInstructor = v.findViewById(R.id.anotherSubTitle);
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
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
