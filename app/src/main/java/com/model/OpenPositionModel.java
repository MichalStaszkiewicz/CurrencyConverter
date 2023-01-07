package com.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "active_positions")
public class OpenPositionModel {
    @PrimaryKey(autoGenerate = true)
    public int openPositionsID;
    @ColumnInfo(name = "short_name")
    private String shortName;
    @ColumnInfo(name = "change")
    private String change;
    @ColumnInfo(name = "profit")
    private String profit;
    @ColumnInfo(name = "full_name")
    private String fullName;
    @ColumnInfo(name = "original_price")
    private String originalPrice;
    @ColumnInfo(name = "quantity")
    private double quantity;



    public OpenPositionModel(String shortName, String change, String profit, String fullName,String originalPrice, double quantity) {
        this.originalPrice = originalPrice;
        this.fullName = fullName;
        this.shortName = shortName;
        this.change = change;
        this.profit = profit;
        this.quantity = quantity;

    }



    public String getShortName() {
        return this.shortName;
    }
    public String getOriginalPrice(){

        return this.originalPrice;
    }
    public String getChange() {
        return this.change;
    }

    public String getProfit() {
        return this.profit;
    }
    public String getFullName(){

       return this.fullName;
    }
    public int getID(){

        return this.openPositionsID;
    }
    public void setID(int id){
        this.openPositionsID  = id;

    }
    public void setChange(String change){
        this.change = change;

    }
    public double getQuantity(){
        return this.quantity;
    }


}
