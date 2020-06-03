package com.emikhalets.datesdb.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "dates_table")
public class DateItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String date;
    private String type;

    public DateItem(String name, String date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
