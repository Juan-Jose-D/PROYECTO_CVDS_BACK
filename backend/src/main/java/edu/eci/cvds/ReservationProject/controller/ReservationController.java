package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import edu.eci.cvds.ReservationProject.service.ReservationService;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/reservation/{id}")
    public Reservation oneReservation(@PathVariable ObjectId id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Reservation> crearReserva(@RequestBody Reservation reservation) {
        Reservation nuevaReserva = reservationService.crearReserva(reservation);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @PutMapping("/reservation/{id}")
    public Reservation updateReservation(@PathVariable ObjectId id, @RequestBody Reservation reservation) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

    @DeleteMapping("/reservation/{id}")
    public void deleteReservation(@PathVariable("id") ObjectId id) {
        reservationRepository.deleteById(id);
    }
}
