package com.example.church;

import com.google.gson.annotations.SerializedName;

public class Member {
    @SerializedName("id")
    private int id;
    
    @SerializedName("first_name")
    private String firstName;
    
    @SerializedName("last_name")
    private String lastName;
    
    private String gender;
    private String status;

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getGender() { return gender; }
    public String getStatus() { return status; }
}
