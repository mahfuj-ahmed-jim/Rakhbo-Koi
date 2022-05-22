package com.premiernoobs.rakhbokoi.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Table")
public class LocalUser {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rowid")
    private int userId;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "password")
    private String password;

    public LocalUser() {
    }

    public LocalUser(String number) {
        this.number = number;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String email) {
        this.number = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

}
