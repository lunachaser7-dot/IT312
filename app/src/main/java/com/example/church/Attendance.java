package com.example.church;

import com.google.gson.annotations.SerializedName;

public class Attendance {
    private int id;
    
    @SerializedName("event_id")
    private int eventId;
    
    @SerializedName("member_id")
    private int memberId;
    
    @SerializedName("attendance_date")
    private String attendanceDate;
    
    private String status; // 'present', 'absent', 'excused'
    private String notes;
    
    @SerializedName("member_name")
    private String memberName; 

    // Constructor for adding new attendance
    public Attendance(int eventId, int memberId, String attendanceDate, String status, String notes) {
        this.eventId = eventId;
        this.memberId = memberId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters for Adapter compatibility
    public String getFullname() { 
        return memberName != null ? memberName : "Member #" + memberId; 
    }
    
    public String getAttendance_date() { 
        return attendanceDate; 
    }

    public int getEventId() { return eventId; }
    public int getMemberId() { return memberId; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
}
