
package com.myapp.attendance_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class CreateCourse extends AppCompatActivity {

    EditText course_name,course_code ;
    Spinner marks_spinner;
    Button btn_save_course;
    db_helper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        course_name = findViewById(R.id.txtCourseName);
        course_code = findViewById(R.id.txtCourseCode);
        marks_spinner =  findViewById(R.id.spinnerMarks);
        btn_save_course = findViewById(R.id.btnSaveCourse);

        database = new db_helper(this);
        btn_save_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _course_name = course_name.getText().toString();
                String _course_code = course_code.getText().toString();
                String course_mark = marks_spinner.getItemAtPosition(marks_spinner.getSelectedItemPosition()).toString();
                Log.e("---------=", course_mark);
                if( _course_name.length() == 0 )  course_name.setError( "Course name is required!" );
                else if ( _course_code.length() == 0 )  course_code.setError( "Course code is required!" );

                Boolean insert_into_course_table = database.insertinto_Course_table(_course_name,_course_code, Integer.parseInt(course_mark));

                if(insert_into_course_table == true){

                    Toast.makeText(CreateCourse.this, "Course Saved Successfully", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(CreateCourse.this, "Course already exist", Toast.LENGTH_LONG).show();

                }



            }
        });

    }

}
