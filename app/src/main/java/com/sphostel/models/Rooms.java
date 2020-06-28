package com.sphostel.models;

/**
 * Created by Apollo on 6/27/2020.
 */

public class Rooms {
    private String Name, Department, Year, Roll, Mobile, Current_Room, New_Room, Reason, Status;

    public Rooms() {
        //empty constructor for receiving fireBase objects
    }

    public Rooms(String name, String department, String year, String roll, String mobile, String current_Room, String new_Room, String reason, String status) {
        Name = name;
        Department = department;
        Year = year;
        Roll = roll;
        Mobile = mobile;
        Current_Room = current_Room;
        New_Room = new_Room;
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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCurrent_Room() {
        return Current_Room;
    }

    public void setCurrent_Room(String current_Room) {
        Current_Room = current_Room;
    }

    public String getNew_Room() {
        return New_Room;
    }

    public void setNew_Room(String new_Room) {
        New_Room = new_Room;
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
