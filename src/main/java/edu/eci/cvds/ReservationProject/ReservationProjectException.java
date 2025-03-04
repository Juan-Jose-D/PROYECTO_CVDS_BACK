package edu.eci.cvds.ReservationProject;

public class ReservationProjectException extends RuntimeException {

    
    public static final String LABORATORY_NOT_FOUND = "Laboratorio no encontrado con id: ";
    public static final String RESERVATION_NOT_FOUND = "Reserva no encontrada con id: ";
    public static final String LABORATORY_RESERVED = "El laboratorio ya está reservado en ese horario.";
    

    public ReservationProjectException(String message) {
        super(message);
    }    
}
