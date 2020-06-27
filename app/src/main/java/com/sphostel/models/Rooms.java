package com.sphostel.models;

/**
 * Created by Apollo on 6/27/2020.
 */

public class Rooms {
    String Name, Department, Year, Roll, Mobile, Current_Room, New_Room, Reason;

    public Rooms() {
        //empty constructor for receiving fireBase objects
    }

    public Rooms(String Name, String Department, String Year, String Roll, String Mobile, String Current_Room, String New_Room, String Reason) {
        this.Name = Name;
        this.Department = Department;
        this.Year = Year;
        this.Roll = Roll;
        this.Mobile = Mobile;
        this.Current_Room = Current_Room;
        this.New_Room = New_Room;
        this.Reason = Reason;
    }

    public String getName() {
        return Name;
    }

    public String getDepartment() {
        return Department;
    }

    public String getYear() {
        return Year;
    }

    public String getRoll() {
        return Roll;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getCurrent_Room() {
        return Current_Room;
    }

    public String getNew_Room() {
        return New_Room;
    }

    public String getReason() {
        return Reason;
    }

}
