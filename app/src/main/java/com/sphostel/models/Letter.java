package com.sphostel.models;

/**
 * Created by Apollo on 6/28/2020.
 */

public class Letter {
    private String Name, Department, Year, Roll, Room, Mobile, Date_From, Date_To, Reason, Status;

    public Letter() {

    }

    public Letter(String name, String department, String year, String roll, String room, String mobile, String date_From, String date_To, String reason, String status) {
        Name = name;
        Department = department;
        Year = year;
        Roll = roll;
        Room = room;
        Mobile = mobile;
        Date_From = date_From;
        Date_To = date_To;
        Reason = reason;
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDate_From() {
        return Date_From;
    }

    public void setDate_From(String date_From) {
        Date_From = date_From;
    }

    public String getDate_To() {
        return Date_To;
    }

    public void setDate_To(String date_To) {
        Date_To = date_To;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
