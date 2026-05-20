package com.example.church;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("role")
    private String role;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public int getUserId() { return userId; }
    public String getRole() { return role; }
}