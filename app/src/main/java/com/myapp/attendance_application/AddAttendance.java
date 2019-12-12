package com.myapp.attendance_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class AddAttendance extends AppCompatActivity {


    private ArrayList<String> courses_list = null;
    private ArrayList<String> student_Ids;
    private String student_name = null;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private View _view;
    private  ArrayList<String> Course_Names = null;

    String student_id ="";
    String date_now = "";
    Spinner courses_spinner;
    db_helper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        database = new db_helper(this);

        Bundle bundle = getIntent().getExtras();
        date_now = bundle.getString("current_date").trim();

        listView = findViewById(R.id.AttendancelistView);

        student_Ids = database.getStudentId();
        if (student_Ids.isEmpty()) {
            Toast.makeText(AddAttendance.this, "Student Id's not available", Toast.LENGTH_SHORT).show();
        }
        adapter = new ArrayAdapter<String>(AddAttendance.this, android.R.layout.simple_expandable_list_item_1, student_Ids);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                student_id = (String)adapterView.getItemAtPosition(position);


                student_name = database.getStudentName_by_id(student_id);
                Log.e("e--- nam",student_name);

                btn_ShowDialog();

            }
        });

    }


    public void btn_ShowDialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(AddAttendance.this);
        _view = getLayoutInflater().inflate(R.layout.activity_attendace_dialog, null);

        dialog.setView(_view);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.setCanceledOnTouchOutside(false);

        Button submit = _view.findViewById(R.id.btnSubmitStatus);
        courses_spinner = _view.findViewById(R.id.spinnerCourses);

        //getCourseNames from db
        courses_list = new ArrayList<>();
        Course_Names = database.getCourseNames();
        if (Course_Names.isEmpty()) {
            Toast.makeText(AddAttendance.this, "Courses not available", Toast.LENGTH_SHORT).show();
        }
        courses_list.add("*select course*");
        courses_list.addAll(Course_Names);

        adapter = new ArrayAdapter<>(AddAttendance.this, android.R.layout.simple_spinner_item, courses_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courses_spinner.setAdapter(adapter);

        alertDialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioGroup statusRadioGroup = _view.findViewById(R.id.radioGroup_ad);
                int selectedId = statusRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = _view.findViewById(selectedId);
                String attendance_status = selectedRadioButton.getText().toString().trim();
                String course = courses_spinner.getItemAtPosition(courses_spinner.getSelectedItemPosition()).toString();

                if(course.equals("*select course*")) {Toast.makeText(AddAttendance.this, "Select valid course", Toast.LENGTH_LONG).show(); return;}

                Boolean insertinto_Attendance_Record_table =  database.insertinto_Attendance_Record_table(student_id, student_name, attendance_status, date_now, course);

                if(insertinto_Attendance_Record_table == true){

                    Toast.makeText(AddAttendance.this, "Attendance Marked", Toast.LENGTH_LONG).show();

                    try{Thread.sleep(1000);}catch (Exception e){e.printStackTrace();}
                    if (attendance_status.trim().equals("Present")) database.update_times_present(student_id, course,student_name);
                    else if(attendance_status.trim().equals("Absent"))  database.update_times_absent(student_id, course, student_name);

                    alertDialog.dismiss();

                }
                else {
                    Toast.makeText(AddAttendance.this, "Error Marking Attendance", Toast.LENGTH_LONG).show();

                }

            }

        });


    }

}
