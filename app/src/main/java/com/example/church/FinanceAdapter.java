package com.example.church;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder> {

    private List<FinanceItem> financeList;

    public FinanceAdapter(List<FinanceItem> financeList) {
        this.financeList = financeList;
    }

    @NonNull
    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_finance, parent, false);
        return new FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceViewHolder holder, int position) {
        FinanceItem item = financeList.get(position);
        holder.tvDesc.setText(item.getDescription());
        holder.tvDate.setText(item.getDate());
        holder.tvAmount.setText(item.getAmount());
        holder.tvType.setText(item.getType());

        // Color and Icon logic
        int color;
        int bgColor;
        if (item.getType() != null && item.getType().equalsIgnoreCase("Income")) {
            color = 0xFF10B981; // Green
            bgColor = 0xFFECFDF3;
        } else {
            color = 0xFFEF4444; // Red
            bgColor = 0xFFFEF2F2;
        }
        
        holder.imgIcon.setColorFilter(color);
        holder.tvAmount.setTextColor(color);
        holder.tvType.setTextColor(color);
        holder.tvType.getBackground().setTint(bgColor);
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    public static class FinanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvDesc, tvDate, tvAmount, tvType;
        android.widget.ImageView imgIcon;

        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvType = itemView.findViewById(R.id.tv_type);
            imgIcon = itemView.findViewById(R.id.imgFinanceIcon);
        }
    }
}
