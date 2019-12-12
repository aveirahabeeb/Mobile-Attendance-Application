package com.myapp.attendance_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Actions extends AppCompatActivity {

    String pass_date_to_intent = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);


        CalendarView calender = findViewById(R.id.calendarView);
        final TextView txt_CurrentDate = findViewById(R.id.txtCurrentDate);
        Button addAttendance_btn = findViewById(R.id.btnAddAttendance);
        Button viewAttendance_btn = findViewById(R.id.btnViewAttendance);
        Button registerStudent_btn = findViewById(R.id.btnRegisterStudent);
        Button btn_add_course = findViewById(R.id.btnAddCourse);
        Button btn_att_summary = findViewById(R.id.btnAttendanceSummary);

        btn_add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Actions.this, CreateCourse.class));

            }
        });

        btn_att_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Actions.this, AttendanceSummary.class));

            }
        });

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                txt_CurrentDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                pass_date_to_intent = dayOfMonth+"/"+(month+1)+"/"+year;
            }
        });

        registerStudent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Actions.this, StudentInfo.class));
            }
        });


        addAttendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date  = txt_CurrentDate.getText().toString().trim();
                if(date.isEmpty() || date.equals("Current Date")){

                    Toast.makeText(Actions.this, "Please choose date", Toast.LENGTH_LONG).show();
                    return;
                }

                    Intent addAttendance = new Intent(Actions.this, AddAttendance.class);
                    Bundle bundle = new Bundle();
                bundle.putString("current_date", pass_date_to_intent);
                addAttendance.putExtras(bundle);
                    startActivity(addAttendance);


            }
        });

        viewAttendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewAttendance = new Intent(Actions.this, ViewAttendance.class);
                startActivity(viewAttendance);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sign_out: {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                try {
                    mAuth.signOut();
                    startActivity(new Intent(Actions.this, LoginActivity.class));
                    finish();
                }catch (Exception e) {

                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return super.onOptionsItemSelected(item);
    }















}
