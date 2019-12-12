package com.myapp.attendance_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AttendanceSummary extends AppCompatActivity {


    private Context context= this;
    private db_helper database = null;
    private  String Attendance_Record_Table = "attendance_record";
    TableLayout tableLayout = null;
    SQLiteDatabase db = null;
    TableRow row = null;
    TableRow rowHeader = null;


    private  ArrayList<String> Course_Names = null;
    private ArrayList<String> courses_list = null;
    private ArrayAdapter<String> adapter;
    Spinner courses_spinner;
    String course_mark = "";
    String course_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_summary);
        database = new db_helper(this);

        setspinner();
        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                courses_spinner.setSelection(0);
                tableLayout.removeAllViewsInLayout();

            }
        });


        courses_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try{

                    course_name = courses_spinner.getItemAtPosition(courses_spinner.getSelectedItemPosition()).toString();
                    if( course_name.trim().equals("*select course*") ){
                        Toast.makeText(AttendanceSummary.this, "please select a valid course", Toast.LENGTH_SHORT).show() ;
                        return;
                    }
                    course_mark = database.getMarkbyCourse(course_name);

                }catch (Exception e){
                    e.printStackTrace();

                }

                tableActions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               // tableLayout.removeAllViewsInLayout();

            }
        });
    }



    public void tableActions(){

        // Reference to TableLayout
        tableLayout = findViewById(R.id.table_main);
        // Add header row
        rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#FAF0E6"));
//        rowHeader.setBackground(getResources().getDrawable(R.drawable.border));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"MATRIC NO","NAMES","NO OF DAYS PRESENT","NO OF DAYS ABSENT","TOTAL MARKS"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        // Start the transaction.
        db = database.getReadableDatabase();
        db.beginTransaction();

        try
        {
            String selectQuery ="select student_id,student_name,times_absent,times_present from "+ Attendance_Record_Table + " where course ='" + course_name + "'  order by times_absent,times_present desc";
            Cursor cursor = db.rawQuery(selectQuery,null);

            if(cursor.getCount()< 0)   Toast.makeText(AttendanceSummary.this, "No record to display", Toast.LENGTH_SHORT).show();

            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
//                    String course= cursor.getString(cursor.getColumnIndex("course"));
                    String student_id = cursor.getString(cursor.getColumnIndex("student_Id"));
                    String student_name= cursor.getString(cursor.getColumnIndex("student_name"));
                    String times_absent= cursor.getString(cursor.getColumnIndex("Times_absent"));
                    String times_present= cursor.getString(cursor.getColumnIndex("Times_present"));
//                    String date= cursor.getString(cursor.getColumnIndex("date_now"));
//                    String status= cursor.getString(cursor.getColumnIndex("attendance_status"));


                    // dara rows
                    row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    String[] colText={student_id, student_name,times_present, times_absent, course_mark};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setTextSize(18);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setPadding(7, 7, 7, 7);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
        finally{
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }

    }



    public void setspinner(){


        courses_spinner = findViewById(R.id.spinner_Courses);

        //getCourseNames from db
        courses_list = new ArrayList<>();
        Course_Names = database.getCourseNames();
        if (Course_Names.isEmpty()) {
            Toast.makeText(AttendanceSummary.this, "Courses not available", Toast.LENGTH_SHORT).show();
        }
        courses_list.add("*select course*");
        courses_list.addAll(Course_Names);

        adapter = new ArrayAdapter<>(AttendanceSummary.this, android.R.layout.simple_spinner_item, courses_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courses_spinner.setAdapter(adapter);

    }








}
