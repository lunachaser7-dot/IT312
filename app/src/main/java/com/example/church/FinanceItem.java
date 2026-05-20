package com.example.church;

import com.google.gson.annotations.SerializedName;

public class FinanceItem {

    @SerializedName("description")
    private String description;
    
    @SerializedName("amount")
    private String amount;
    
    @SerializedName("transaction_date")
    private String date;
    
    @SerializedName("type")
    private String type; // Income / Expense

    public FinanceItem(String description, String amount, String date, String type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public String getDescription() { return description; }
    public String getAmount() { return amount; }
    public String getDate() { return date; }
    public String getType() { return type; }
}
