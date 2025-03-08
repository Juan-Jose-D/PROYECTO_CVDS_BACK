package edu.eci.cvds.ReservationProject.model;

// Clase que representa la respuesta del login
public class LoginResponse {
    private boolean success;
    private String message;

    public LoginResponse(boolean success, String message) {
        this.success = success;
    }

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserType() {
        return message;
    }

    public void setUserType(String message) {
        this.message = message;
    }
}
