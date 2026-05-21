package com.example.church;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FinanceAdapter adapter;
    private List<FinanceItem> financeList;
    private TextView txtTitle, txtSub, txtRecent;
    private CardView cardSummary;
    private android.widget.ImageButton btnBack;
    private com.google.android.material.button.MaterialButton btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerViewFinance);
        txtTitle = findViewById(R.id.txtFinanceTitle);
        txtSub = findViewById(R.id.txtFinanceSub);
        txtRecent = findViewById(R.id.txtRecentTrans);
        cardSummary = findViewById(R.id.cardFinanceSummary);
        btnBack = findViewById(R.id.btnBackFinance);
        btnReturn = findViewById(R.id.btnReturnFinance);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample Finance Data
        financeList = new ArrayList<>();
        financeList.add(new FinanceItem("Sunday Offering", "₱ 12,500", "2026-05-11", "Income"));
        financeList.add(new FinanceItem("Utility Bills", "₱ 3,200", "2026-05-10", "Expense"));
        financeList.add(new FinanceItem("Mission Fund", "₱ 8,000", "2026-05-09", "Income"));
        financeList.add(new FinanceItem("Maintenance", "₱ 1,800", "2026-05-08", "Expense"));
        financeList.add(new FinanceItem("Special Donation", "₱ 15,000", "2026-05-07", "Income"));
        financeList.add(new FinanceItem("Choir Supplies", "₱ 1,400", "2026-05-06", "Expense"));

        // Set Adapter
        adapter = new FinanceAdapter(financeList);
        recyclerView.setAdapter(adapter);

        // Fetch from API
        fetchFinanceData();

        // Apply Animations
        applyFinanceAnimations();

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

    private void fetchFinanceData() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getTransactions().enqueue(new Callback<List<FinanceItem>>() {
            @Override
            public void onResponse(Call<List<FinanceItem>> call, Response<List<FinanceItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    financeList.clear();
                    financeList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<FinanceItem>> call, Throwable t) {
                // Keep sample data if API fails or show error
            }
        });
    }

    private void applyFinanceAnimations() {
        // Initial state
        txtTitle.setAlpha(0f);
        txtTitle.setTranslationY(-30f);
        txtSub.setAlpha(0f);
        txtSub.setTranslationY(-30f);
        
        cardSummary.setAlpha(0f);
        cardSummary.setScaleX(0.9f);
        cardSummary.setScaleY(0.9f);
        
        txtRecent.setAlpha(0f);
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(50f);

        // Animate
        txtTitle.animate().alpha(1f).translationY(0f).setDuration(600).start();
        txtSub.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(200).start();
        
        cardSummary.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setStartDelay(400)
                .start();
                
        txtRecent.animate().alpha(1f).setDuration(500).setStartDelay(600).start();
        
        recyclerView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(800)
                .start();
    }
}
