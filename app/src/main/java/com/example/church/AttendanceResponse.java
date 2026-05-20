package com.example.church;

import com.google.gson.annotations.SerializedName;

public class AttendanceResponse {

    @SerializedName("status")
    private String status;  // e.g., "success" or "error"

    @SerializedName("message")
    private String message; // e.g., "Attendance saved successfully"

    // Constructor
    public AttendanceResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
