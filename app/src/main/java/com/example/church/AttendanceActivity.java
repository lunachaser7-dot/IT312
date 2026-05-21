package com.example.church;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<Attendance> attendanceList;

    private EditText etMemberId; 
    private EditText etFullName;
    private EditText etEventId;
    private EditText etNotes;
    private Spinner spinnerStatus;
    private Button btnAddAttendance, btnReturn;
    private android.widget.ImageButton btnBack;
    private View appBar;
    private CardView inputCard;
    private TextView txtLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Initialize UI Elements
        etMemberId = findViewById(R.id.etMemberId); 
        etFullName = findViewById(R.id.etFullName);
        etEventId = findViewById(R.id.etEventId);
        etNotes = findViewById(R.id.etNotes);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnAddAttendance = findViewById(R.id.btnAddAttendance);
        btnReturn = findViewById(R.id.btnReturnDashboard);
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView);
        appBar = findViewById(R.id.appBarAttendance);
        inputCard = findViewById(R.id.cardAttendanceInput);
        txtLogs = findViewById(R.id.txtRecentLogs);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Prepare initial data
        attendanceList = new ArrayList<>();

        // Set Adapter
        adapter = new AttendanceAdapter(attendanceList);
        recyclerView.setAdapter(adapter);

        // Apply Animations
        applyAttendanceAnimations();

        // Fetch Data from API
        fetchAttendanceData();

        // Add Button Click Listener
        btnAddAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemberAttendance();
            }
        });

        // Back Button Listener
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Go back to Dashboard
                }
            });
        }

        // Return to Dashboard Button Listener
        if (btnReturn != null) {
            btnReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Return to Dashboard
                }
            });
        }
    }

    private void applyAttendanceAnimations() {
        appBar.setAlpha(0f);
        appBar.setTranslationY(-50f);

        inputCard.setAlpha(0f);
        inputCard.setScaleX(0.9f);
        inputCard.setScaleY(0.9f);

        txtLogs.setAlpha(0f);
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(50f);

        appBar.animate().alpha(1f).translationY(0f).setDuration(600).start();

        inputCard.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setStartDelay(300)
                .start();

        txtLogs.animate().alpha(1f).setDuration(500).setStartDelay(500).start();

        recyclerView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(700)
                .start();
    }

    private void addMemberAttendance() {
        String memberIdStr = etMemberId.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String eventIdStr = etEventId != null ? etEventId.getText().toString().trim() : "1";
        String notes = etNotes != null ? etNotes.getText().toString().trim() : "";
        String status = spinnerStatus.getSelectedItem().toString().toLowerCase();
        
        // Combine full name with notes for the database record
        String finalNotes = notes;
        if (!fullName.isEmpty()) {
            finalNotes = "Member: " + fullName + (notes.isEmpty() ? "" : " | " + notes);
        }

        // Match SQL Date Format (YYYY-MM-DD)
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (memberIdStr.isEmpty()) {
            etMemberId.setError("Member ID is required");
            etMemberId.requestFocus();
            return;
        }

        try {
            int memberId = Integer.parseInt(memberIdStr);
            int eventId = eventIdStr.isEmpty() ? 1 : Integer.parseInt(eventIdStr);

            Attendance newAttendance = new Attendance(eventId, memberId, currentDate, status, finalNotes);

            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            apiService.addAttendance(newAttendance).enqueue(new Callback<AttendanceResponse>() {
                @Override
                public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                    if (response.isSuccessful()) {
                        fetchAttendanceData();
                        etMemberId.setText("");
                        etFullName.setText("");
                        if (etNotes != null) etNotes.setText("");
                        Toast.makeText(AttendanceActivity.this, "Attendance saved!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show real error from server
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown Error";
                            Toast.makeText(AttendanceActivity.this, "Error: " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AttendanceActivity.this, "Error: check IDs (Member/Event)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    Toast.makeText(AttendanceActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NumberFormatException e) {
            etMemberId.setError("Please enter valid numbers");
        }
    }

    private void fetchAttendanceData() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Attendance>> call = apiService.getAttendance();

        call.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    attendanceList.clear();
                    attendanceList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {
                Toast.makeText(AttendanceActivity.this, "Failed to load logs: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
