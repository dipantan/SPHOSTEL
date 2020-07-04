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
    String photoUrl;

    public Student() {
        //empty constructor
    }

    public Student(String name, String department, String academicYear, String roll_no, String room_no, String dob, String mobile_no, String email_id, String emergency_no, String blood_group, String photoUrl) {
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
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getEmergency_no() {
        return emergency_no;
    }

    public void setEmergency_no(String emergency_no) {
        this.emergency_no = emergency_no;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
