package edu.eci.cvds.ReservationProject.model;

public class LoginResponse {
    private boolean success;
    private String role;
    private String token;

    public LoginResponse(boolean success, String role, String token) {
        this.success = success;
        this.role = role;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}