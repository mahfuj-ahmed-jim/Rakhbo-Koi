package com.premiernoobs.rakhbokoi.Class.Firebase;

public class FirebaseOtp {

    private String email;
    private int otp;
    private String time;

    public FirebaseOtp() {
    }

    public FirebaseOtp(String email, int otp, String time) {
        this.email = email;
        this.otp = otp;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
