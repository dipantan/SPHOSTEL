package com.sphostel.models;

/**
 * Created by Apollo on 6/27/2020.
 */

public class Rooms {
    String sName, sDept, sYear, sRoll, sMobile, sCRoom, sNRoom, sReason;

    public Rooms() {
        //empty constructor for receiving fireBase objects
    }

    public Rooms(String sName, String sDept, String sYear, String sRoll, String sMobile, String sCRoom, String sNRoom, String sReason) {
        this.sName = sName;
        this.sDept = sDept;
        this.sYear = sYear;
        this.sRoll = sRoll;
        this.sMobile = sMobile;
        this.sCRoom = sCRoom;
        this.sNRoom = sNRoom;
        this.sReason = sReason;
    }

    public String getsName() {
        return sName;
    }

    public String getsDept() {
        return sDept;
    }

    public String getsYear() {
        return sYear;
    }

    public String getsRoll() {
        return sRoll;
    }

    public String getsMobile() {
        return sMobile;
    }

    public String getsCRoom() {
        return sCRoom;
    }

    public String getsNRoom() {
        return sNRoom;
    }

    public String getsReason() {
        return sReason;
    }

}
