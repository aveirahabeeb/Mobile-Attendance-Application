package com.myapp.attendance_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import model.Attendance_model;


public class ViewAttendance extends AppCompatActivity {


    ArrayList<Attendance_model>  data;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SearchView searchView;
    String date;
    db_helper database ;
    Button search_student_name;
    EditText student_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);


        database = new db_helper(this);
        initializeViews();
        loadData();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View itemView, int position) {

                        TextView record_to_delete =  itemView.findViewById(R.id.student_id);
                        String _id = record_to_delete.getText().toString();
                        String student_id = _id.substring(_id.lastIndexOf(":") + 1);
                        AlertDialog diaBox = AskOption(student_id.trim());
                        diaBox.show();

                    }
                }));



            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(ViewAttendance.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date = (dayOfMonth)+ "/"+(monthOfYear+1)
                                    +"/"+(year);
                            if((date!= null) || (date!= "") ){

                                searchView.post(new Runnable() {

                                    @Override
                                    public void run() {

                                        searchView.setQuery(date, false);

                                    }
                                });

                            }

                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            });


           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

               @Override
               public boolean onQueryTextSubmit(String query_param) {

                   search(query_param);
                   searchView.onActionViewCollapsed();
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String s) {
                   return false;
               }
           });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {

                    recyclerView.setAdapter(new AdapterClass(data));
                    return false;
                }
            });

        search_student_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _student_name = student_name.getText().toString().trim();
                ArrayList<Attendance_model> student_name_data = database.SearchbyStudentName(_student_name);
                if (student_name_data.isEmpty()) {
                    Toast.makeText(ViewAttendance.this, "Record does not exist", Toast.LENGTH_SHORT).show() ;
                }
                recyclerView.setAdapter(new AdapterClass(student_name_data));

            }
        });


    }



    private void initializeViews(){

        searchView = findViewById(R.id.SearchView_date);
        recyclerView = findViewById(R.id.RecylerViewAttendance);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        search_student_name = findViewById(R.id.btn_search_name);
        student_name = findViewById(R.id.txtsearch_name);

    }

    public void loadData(){

        data = database.getAllAttendance_Record();
        if (data.isEmpty()) {
            Toast.makeText(ViewAttendance.this, "No Record available", Toast.LENGTH_SHORT).show() ;
        }
        recyclerView.setAdapter(new AdapterClass(data));
    }

    private void search(String str){

        ArrayList<Attendance_model>  attendancedata = database.searchAttendanceByDate(str);

        if (attendancedata.isEmpty()) {
            Toast.makeText(ViewAttendance.this, "Record does not exist", Toast.LENGTH_SHORT).show() ;
        }
        Log.e("EPAMEJI----", attendancedata.toString());
        recyclerView.setAdapter(new AdapterClass(attendancedata));


    }

    private AlertDialog AskOption(final String student_id) {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage(":question: You are about to Delete Attendance Record")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        database.deleteAttendance(student_id);
                        Toast.makeText(ViewAttendance.this, "Attendance record deleted ", Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }


}
