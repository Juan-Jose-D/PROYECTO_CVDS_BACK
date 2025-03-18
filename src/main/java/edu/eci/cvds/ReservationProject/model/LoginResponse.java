package edu.eci.cvds.ReservationProject.model;

public class LoginResponse {
    private boolean success;
    private String message;
    private String userRole; // Cambiado de userType a userRole

    public LoginResponse(boolean success, String userRole) {
        this.success = success;
        this.userRole = userRole;
        this.message = success ? "Credenciales correctas" : "Credenciales incorrectas";
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}