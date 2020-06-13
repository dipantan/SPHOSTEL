package com.sphostel.models;

/**
 * Created by Apollo on 6/10/2020.
 */

public class Student {
    String name;
    String department;
    String academicYear;
    String roll_no;
    String room_no;
    String dob;
    String mobile_no;
    String email_id;
    String emergency_no;
    String blood_group;

    public Student() {
        //empty constructor
    }

    public Student(String name, String department, String academicYear, String roll_no, String room_no, String dob, String mobile_no, String email_id, String emergency_no, String blood_group) {
        this.name = name;
        this.department = department;
        this.academicYear = academicYear;
        this.roll_no = roll_no;
        this.room_no = room_no;
        this.dob = dob;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.emergency_no = emergency_no;
        this.blood_group = blood_group;
        //  this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getDob() {
        return dob;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getEmergency_no() {
        return emergency_no;
    }

    public String getBlood_group() {
        return blood_group;
    }
}
