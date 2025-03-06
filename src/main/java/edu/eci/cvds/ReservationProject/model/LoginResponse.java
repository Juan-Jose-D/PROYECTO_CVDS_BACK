package edu.eci.cvds.ReservationProject.model;

// Clase que representa la respuesta del login
public class LoginResponse {
    private boolean success;
    private String userType;

    public LoginResponse(boolean success, String userType) {
        this.success = success;
        this.userType = userType;
    }

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
