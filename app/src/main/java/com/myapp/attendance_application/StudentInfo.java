package com.myapp.attendance_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class StudentInfo extends AppCompatActivity {

    EditText Email_address;
    EditText Id;
    EditText Name;
    EditText Address;
    EditText Age;
    RadioGroup RadioGroup;
    Button Save;
    db_helper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        database = new db_helper(this);
        Email_address =  findViewById(R.id.txtEmail);
        Id =  findViewById(R.id.txtStudentId);
        Name = findViewById(R.id.txtStudentName);
        Address = findViewById(R.id.txtStudentAddress);
        Age =  findViewById(R.id.txtAge);
        Save =  findViewById(R.id.btnSaveStudent);
        RadioGroup = findViewById(R.id.RadiobtnGroup);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = RadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                final String address = Address.getText().toString();
                final String gender = selectedRadioButton.getText().toString();
                final String age = Age.getText().toString();
                final String id= Id.getText().toString();
                final String name = Name.getText().toString();
                final String std_email = Email_address.getText().toString();


                if( address.isEmpty() )  Address.setError( "Address is required!" );
                else if ( age.isEmpty() )  Age.setError( "Age is required!" );
                else if( id.isEmpty() )  Id.setError( "Id is required!" );
                else if(name.isEmpty() )  Address.setError( "Name is required!" );
                else if(std_email.isEmpty() )  Address.setError( "Email is required!" );
                else{

                    Boolean insert_into_student_info__table = database.insert_into_studentInfo_table( address, age,  gender,  id, name, std_email);
                    if(insert_into_student_info__table == true) Toast.makeText(StudentInfo.this, "Student data Saved Successfully", Toast.LENGTH_LONG).show();
                    else Toast.makeText(StudentInfo.this, "Error Saving Student data ", Toast.LENGTH_LONG).show();

                }

            }
        });



    }
}
