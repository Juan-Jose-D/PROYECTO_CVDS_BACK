package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import edu.eci.cvds.ReservationProject.ReservationProjectException;

/**
 * Servicio para gestionar las reservas en la aplicación.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LaboratoryService laboratoryService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, LaboratoryService laboratoryService) {
        this.reservationRepository = reservationRepository;
        this.laboratoryService = laboratoryService;
    }

    /**
     * Crea una nueva reserva y la guarda en la base de datos.
     * Valida que no haya conflictos de horario.
     *
     * @param reservation La reserva a crear.
     * @return La reserva guardada.
     */
    public Reservation createReservation(Reservation reservation) {
        List<Reservation> existingReservations = reservationRepository.findByDateAndInitialTimeLessThanEqualAndFinalTimeGreaterThanEqualAndLaboratory(
                reservation.getDate(),
                reservation.getInitialTime(),
                reservation.getFinalTime(),
                reservation.getLaboratory()
        );

        if (!existingReservations.isEmpty()) {
            throw new ReservationProjectException(ReservationProjectException.LABORATORY_RESERVED);
        }

        reservation.setId(new ObjectId());
        reservation.setStatus(true);
        return reservationRepository.save(reservation);
    }


    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de reservas.
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id Identificador de la reserva.
     * @return La reserva encontrada o una excepción si no existe.
     */
    public Reservation getReservationById(ObjectId id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.RESERVATION_NOT_FOUND + id));
    }

    /**
     * Modifica una reserva existente.
     *
     * @param id          Identificador de la reserva a modificar.
     * @param reservation Datos actualizados de la reserva.
     * @return La reserva modificada.
     */
    public Reservation updateReservation(ObjectId id, Reservation reservation) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationProjectException(ReservationProjectException.RESERVATION_NOT_FOUND + id);
        }
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id Identificador de la reserva a eliminar.
     */
    public void deleteReservation(ObjectId id) {
        reservationRepository.deleteById(id);
    }


        /**
     * Verifica si un laboratorio está disponible para una fecha y hora específica.
     * 
     * @param laboratoryId ID del laboratorio a verificar.
     * @param date Fecha para la consulta.
     * @param time Hora para la consulta.
     * @return true si el laboratorio está disponible, false si no.
     */
    public boolean isLaboratoryAvailable(ObjectId laboratoryId, LocalDate date, LocalTime time) {

    if (laboratoryId == null || date == null || time == null) {
        throw new IllegalArgumentException("Los parámetros no pueden ser nulos.");
    }

    Date queryDate = Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
    List<Reservation> reservations = reservationRepository.findByLaboratoryIdAndDate(laboratoryId, queryDate);

    if (reservations.isEmpty()) {
        return true;
    }

    for (Reservation reservation : reservations) {
        try {
            LocalTime reservationStart = LocalTime.parse(reservation.getInitialTime());
            LocalTime reservationEnd = LocalTime.parse(reservation.getFinalTime());

            if ((time.isAfter(reservationStart) || time.equals(reservationStart)) 
                && (time.isBefore(reservationEnd) || time.equals(reservationEnd))) {
                return false;
        }
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear la hora: " + e.getMessage());
            return false;
        }
    }

    return true;
}
}
