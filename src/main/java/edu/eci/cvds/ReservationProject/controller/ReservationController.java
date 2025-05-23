package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.service.ReservationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@CrossOrigin(
    origins = {
        "https://frontdespliegue-gae9f9b2aaedfabw.eastus-01.azurewebsites.net",
        "https://proyectoreservascvdsfrontend-fxgqetgfcsagbqhf.eastus-01.azurewebsites.net", 
        "http://localhost:3000", 
        "http://localhost:5500"
    }, 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
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
    @DeleteMapping
    public ResponseEntity<?> deleteReservation(
        @RequestParam String labId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
        @RequestParam @DateTimeFormat(pattern = "HH:mm") String time) {
        try {
            ObjectId laboratoryId = new ObjectId(labId);
            LocalDate requestDate = LocalDate.parse(date);
            Date convertedDate = Date.from(requestDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            reservationService.deleteReservationByLabDateAndTime(laboratoryId, convertedDate, time);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de laboratorio inválido");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de fecha/hora incorrecto. Use yyyy-MM-dd y HH:mm");
        } catch (ReservationProjectException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


/**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta con estado HTTP 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        ObjectId reservationId = new ObjectId(id);
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }


    /**
     * Verifica la disponibilidad de un laboratorio para una fecha y hora específicas.
     *
     * @param labId ID del laboratorio a verificar.
     * @param date Fecha para la consulta (formato: yyyy-MM-dd).
     * @param time Hora para la consulta (formato: HH:mm).
     * @return ResponseEntity con la disponibilidad y mensaje.
     */
    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(
            @RequestParam String labId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") String time) { 
        try {
            ObjectId laboratoryId = new ObjectId(labId);
            LocalDate requestDate = LocalDate.parse(date); 
            LocalTime requestTime = LocalTime.parse(time); 
            
            boolean isAvailable = reservationService.isLaboratoryAvailable(laboratoryId, requestDate, requestTime);
            
            Map<String, Object> response = new HashMap<>();
            response.put("available", isAvailable);
            
            if (!isAvailable) {
                response.put("message", "El laboratorio ya tiene una reserva programada para ese horario.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "ID de laboratorio inválido: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (DateTimeParseException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Formato de fecha u hora inválido. Use yyyy-MM-dd y HH:mm.");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al verificar disponibilidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
