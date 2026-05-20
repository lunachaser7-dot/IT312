package com.example.church;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // ==========================================
    // 1. ATTENDANCE ENDPOINTS
    // ==========================================

    // Fetches attendance logs
    @GET("api/attendance")
    Call<List<Attendance>> getAttendance();

    // Saves a new attendance record (matching event_id, member_id, status, etc.)
    @POST("api/attendance")
    Call<AttendanceResponse> addAttendance(@Body Attendance attendance);


    // ==========================================
    // 2. FINANCE / TRANSACTION ENDPOINTS
    // ==========================================

    // Fetches transactions (Tithes, Offerings, Expenses)
    @GET("api/finance")
    Call<List<FinanceItem>> getTransactions();

    // Records a new transaction to the ledger
    @POST("api/finance")
    Call<FinanceResponse> addTransaction(@Body FinanceItem transaction);


    // ==========================================
    // 3. MEMBER MANAGEMENT ENDPOINTS
    // ==========================================

    // Fetches the church member roster
    @GET("api/members")
    Call<List<Member>> getMembers();


    // ==========================================
    // 4. AUTHENTICATION ENDPOINTS
    // ==========================================

    // Validates credentials against the users table
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}