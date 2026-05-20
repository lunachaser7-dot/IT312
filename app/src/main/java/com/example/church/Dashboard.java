package com.example.church;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Dashboard extends AppCompatActivity {

    private CardView cardAttendance, cardFinance, cardSummary;
    private TextView txtTitle, txtSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Views
        cardAttendance = findViewById(R.id.card_attendance);
        cardFinance = findViewById(R.id.card_finance);
        cardSummary = findViewById(R.id.card_summary);
        txtTitle = findViewById(R.id.txtDashTitle);
        txtSub = findViewById(R.id.txtDashSub);

        // Apply Animations
        applyDashboardAnimations();

        // Click Listeners
        if (cardAttendance != null) {
            cardAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, AttendanceActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (cardFinance != null) {
            cardFinance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, FinanceActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void applyDashboardAnimations() {
        // Initial state
        txtTitle.setAlpha(0f);
        txtTitle.setTranslationX(-50f);
        txtSub.setAlpha(0f);
        txtSub.setTranslationX(-50f);
        
        cardAttendance.setAlpha(0f);
        cardAttendance.setTranslationY(100f);
        cardFinance.setAlpha(0f);
        cardFinance.setTranslationY(100f);
        cardSummary.setAlpha(0f);
        cardSummary.setTranslationY(100f);

        // Animate Title and Subtitle
        txtTitle.animate().alpha(1f).translationX(0f).setDuration(600).start();
        txtSub.animate().alpha(1f).translationX(0f).setDuration(600).setStartDelay(200).start();

        // Animate Cards (Staggered)
        cardAttendance.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(400).start();
        cardFinance.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(600).start();
        cardSummary.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(800).start();
    }
}
