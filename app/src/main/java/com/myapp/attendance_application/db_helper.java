package com.myapp.attendance_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import model.Attendance_model;


public class db_helper extends SQLiteOpenHelper {

    private static String Attendance_DB = "attendance.db";
    private static String course_Table = "course_data_table";
    private static String Attendance_Record_Table = "attendance_record";
    private static String Student_info_Table = "student_info";


    SQLiteDatabase db = null;

    public db_helper(Context context){

        super(context, Attendance_DB, null , 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+course_Table+"(course_name text primary key, course_code text,course_mark text)");
       db.execSQL("create table "+Attendance_Record_Table+"(student_Id text, student_name text, attendance_status text, date_now text, course text, Times_absent integer default 0, Times_present integer default 0)");
        db.execSQL("create table "+Student_info_Table+"(address text, age text, gender text, id text primary key, name text, email text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+course_Table);
        db.execSQL("drop table if exists "+Attendance_Record_Table);
        db.execSQL("drop table if exists "+Student_info_Table );
        onCreate(db);
    }


    public ArrayList<Attendance_model> getAllAttendance_Record() {
        ArrayList<Attendance_model> att_model_list ;

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+ Attendance_Record_Table  , null);
            cursor.moveToFirst();

            att_model_list = new ArrayList<>();
            while (cursor.isAfterLast() == false){

                Attendance_model att_model = new Attendance_model();
                att_model.setId(cursor.getString(cursor.getColumnIndex("student_Id")));
                att_model.setName(cursor.getString(cursor.getColumnIndex("student_name")));
                att_model.setStatus(cursor.getString(cursor.getColumnIndex("attendance_status")));
                att_model.setDate(cursor.getString(cursor.getColumnIndex("date_now")));
                att_model.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                att_model_list.add(att_model);
                System.err.println("getAllAttendan------\n" + att_model_list );
                cursor.moveToNext();
            };

        } catch (Exception e) {
            att_model_list = null;
            e.printStackTrace();
        }
        return att_model_list;

    }



    //Get_Student_Id
    public ArrayList<String> getStudentId() {

        ArrayList<String> std_model = new ArrayList<>();

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select id from "+Student_info_Table , null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                std_model.add(cursor.getString(cursor.getColumnIndex("id")));
                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            std_model = null;
            e.printStackTrace();
        }
        return std_model;
    }

    //Insert into studentinfo_db table
    public boolean insert_into_studentInfo_table(String address,String age, String gender, String id,String name,String std_email) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("email", std_email);
        long _insert = db.insert(Student_info_Table, null, contentValues);
        if (_insert == -1) return false;
        else return true;

    }

    //Get_Student_Name
    public String getStudentName_by_id(String id) {

        String stdmodel = "" ;

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select name from "+ Student_info_Table+ " where id ='" + id + "' " , null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                stdmodel = (cursor.getString(cursor.getColumnIndex("name")));
                Log.e("nameeeee------" , stdmodel);

                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            stdmodel = null;
            e.printStackTrace();
        }
        return stdmodel;
    }

    //Get_Course_Names
    public ArrayList<String> getCourseNames() {

        ArrayList<String> list_of_courses = new ArrayList<String>();
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select course_name from "+ course_Table , null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                list_of_courses.add(cursor.getString(cursor.getColumnIndex("course_name")));
                Log.e("list_of_courses------" , list_of_courses.toString());

                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            list_of_courses = null;
            e.printStackTrace();
        }
        return list_of_courses;
    }

    //Insert into Attendance_record_table
    public boolean insertinto_Attendance_Record_table(String student_Id, String student_name, String attendance_status, String date_now, String course) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", student_Id);
        contentValues.put("student_name", student_name);
        contentValues.put("attendance_status", attendance_status);
        contentValues.put("date_now", date_now);
        contentValues.put("course", course);
        long insert = db.insert(Attendance_Record_Table, null, contentValues);
        if (insert == -1) return false;
        else return true;

    }

    //Insert into course_db table
    public boolean insertinto_Course_table( String course_name,  String course_code, int course_mark) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_name", course_name);
        contentValues.put("course_code", course_code);
        contentValues.put("course_mark", course_mark);
        long insert = db.insert(course_Table, null, contentValues);
        if (insert == -1) return false;
        else return true;

    }

    public void update_times_present(String studentId, String course, String student_name){
        try{
            db = this.getReadableDatabase();
            String strSQL = "UPDATE "+ Attendance_Record_Table+ " SET Times_present = Times_present + 1 WHERE student_Id ='" + studentId + "' and course ='" + course + "' and student_name ='" + student_name + "'  " ;
            db.execSQL(strSQL);
        }catch (Exception e){

            e.printStackTrace();
        }

    }
    public void update_times_absent(String studentId, String course, String student_name){

        try{
            db = this.getReadableDatabase();
            String strSQL = "UPDATE "+ Attendance_Record_Table+ " SET Times_absent = Times_absent + 1 WHERE student_Id ='" + studentId + "' and course ='" + course + "' and student_name ='" + student_name + "'   " ;
            db.execSQL(strSQL);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //search_Attendance_By_Date
    public ArrayList<Attendance_model> searchAttendanceByDate(String date) {

        ArrayList<Attendance_model> attmodel = new ArrayList<>();

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+Attendance_Record_Table+ " where date_now='" + date + "'  ", null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                Attendance_model attendance_data = new Attendance_model();
                attendance_data.setId(cursor.getString(cursor.getColumnIndex("student_Id")));
                attendance_data.setName(cursor.getString(cursor.getColumnIndex("student_name")));
                attendance_data.setStatus(cursor.getString(cursor.getColumnIndex("attendance_status")));
                attendance_data.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                attendance_data.setDate(cursor.getString(cursor.getColumnIndex("date_now")));

//                attendance_data.setPresent(cursor.getInt(cursor.getColumnIndex("Times_present")));
//                attendance_data.setAbsent(cursor.getInt(cursor.getColumnIndex("Times_absent")));
                attmodel.add(attendance_data);
                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            attmodel = null;
            e.printStackTrace();
        }
        return attmodel;
    }


    // getMarkbyCourse
    public String getMarkbyCourse(String course_name) {

        String  course_model = null;

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select course_mark from "+course_Table+ " where course_name ='" + course_name + "' "  , null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                course_model = cursor.getString(cursor.getColumnIndex("course_mark"));
                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            course_model = null;
            e.printStackTrace();
        }
        return course_model;
    }

    //Delete Attendance
    public void deleteAttendance(String student_id ) {
        db = this.getWritableDatabase();
        String query =  "delete from "+ Attendance_Record_Table + " where student_id='" + student_id + "' ";
        db.execSQL(query);
        db.close();

    }

    // SearchbyStudentName
    public ArrayList<Attendance_model>  SearchbyStudentName(String student_name) {

        ArrayList<Attendance_model> data = new ArrayList<>();


        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+Attendance_Record_Table+ " where student_name ='" + student_name + "' "  , null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false){

                Attendance_model attendance_data = new Attendance_model();
                attendance_data.setId(cursor.getString(cursor.getColumnIndex("student_Id")));
                attendance_data.setName(cursor.getString(cursor.getColumnIndex("student_name")));
                attendance_data.setStatus(cursor.getString(cursor.getColumnIndex("attendance_status")));
                attendance_data.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                attendance_data.setDate(cursor.getString(cursor.getColumnIndex("date_now")));
                data.add(attendance_data);
                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            data = null;
            e.printStackTrace();
        }
        return data;
    }


}












