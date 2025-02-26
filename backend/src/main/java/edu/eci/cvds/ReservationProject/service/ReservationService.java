package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation crearReserva(Reservation reservation) {
        reservation.setId(new ObjectId());
        return reservationRepository.save(reservation);
    }
}
