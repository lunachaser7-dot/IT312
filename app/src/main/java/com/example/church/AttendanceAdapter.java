package com.example.church;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<Attendance> attendanceList;

    public AttendanceAdapter(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        holder.txtName.setText(attendance.getFullname());
        holder.txtDate.setText(attendance.getAttendance_date());
        holder.txtStatus.setText(attendance.getStatus());

        // Set Initial
        if (attendance.getFullname() != null && !attendance.getFullname().isEmpty()) {
            holder.txtInitial.setText(String.valueOf(attendance.getFullname().charAt(0)).toUpperCase());
        }

        // Status Styling
        int color;
        int bgColor;
        switch (attendance.getStatus()) {
            case "Present":
                color = 0xFF10B981; // Green
                bgColor = 0xFFECFDF3;
                break;
            case "Absent":
                color = 0xFFEF4444; // Red
                bgColor = 0xFFFEF2F2;
                break;
            case "Late":
                color = 0xFFF59E0B; // Orange
                bgColor = 0xFFFFFBEB;
                break;
            default:
                color = 0xFF64748B;
                bgColor = 0xFFF1F5F9;
                break;
        }
        holder.txtStatus.setTextColor(color);
        holder.txtStatus.getBackground().setTint(bgColor);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDate, txtStatus, txtInitial;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtInitial = itemView.findViewById(R.id.txtInitial);
        }
    }
}
