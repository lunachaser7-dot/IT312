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
    
    @SerializedName("member_full_name")
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
        if (memberName != null && !memberName.isEmpty() && !memberName.equals("null null")) {
            return memberName;
        }
        
        // Smart extract from notes if name is missing in joined table
        if (notes != null && notes.startsWith("Member: ")) {
            String[] parts = notes.split(" \\| ");
            return parts[0].replace("Member: ", "");
        }
        
        return "Member #" + memberId; 
    }
    
    public String getCleanNotes() {
        if (notes != null && notes.startsWith("Member: ")) {
            String[] parts = notes.split(" \\| ");
            return parts.length > 1 ? parts[1] : "—";
        }
        return notes != null ? notes : "—";
    }
    
    public String getAttendance_date() { 
        return attendanceDate; 
    }

    public int getEventId() { return eventId; }
    public int getMemberId() { return memberId; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
}
