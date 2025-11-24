package com.onevoice.management.onevoice.model;

public class OtpRequest {
    private String email;
    private String otp;
    private Panchayath panchayath;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Panchayath getPanchayath() {
        return panchayath;
    }
    public void setPanchayath(Panchayath panchayath) {
        this.panchayath = panchayath;
    }
}
