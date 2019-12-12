package com.myapp.attendance_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Attendance_model;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

     ArrayList<Attendance_model> list;

     public AdapterClass(ArrayList<Attendance_model> list){

         this.list = list;
     }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new AdapterClass.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {

        holder.id.setText("Id: " + list.get(position).getId());
        holder.name.setText("Name:" +  list.get(position).getName());
        holder.status.setText("Status: " + list.get(position).getStatus());
        holder.date.setText("Date: " + list.get(position).getDate());
        holder.course.setText("Course: " + list.get(position).getCourse());


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         TextView id, name, status, date, course ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.student_id);
            name = itemView.findViewById(R.id.student_name);
            status = itemView.findViewById(R.id.student_status);
            date = itemView.findViewById(R.id.date);
            course =  itemView.findViewById(R.id.course);



        }
    }
}
