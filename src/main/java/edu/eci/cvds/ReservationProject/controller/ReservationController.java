package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import edu.eci.cvds.ReservationProject.service.ReservationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * Controlador REST para gestionar las reservas.
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
     /**
     * Crea una nueva reserva.
     *
     * @param reservation Reserva a crear.
     * @return La reserva creada con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    /**
     * Obtiene todas las reservas.
     *
     * @return Lista de reservas.
     */
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva.
     * @return La reserva encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable ObjectId id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    /**
     * Modifica una reserva existente.
     *
     * @param id          ID de la reserva a modificar.
     * @param reservation Datos actualizados de la reserva.
     * @return La reserva actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable ObjectId id, @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservation));
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta con estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable ObjectId id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
