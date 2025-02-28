package edu.eci.cvds.ReservationProject.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import edu.eci.cvds.ReservationProject.model.Reservation;

/**
 * Repositorio para gestionar las operaciones CRUD de las reservas en la base de datos MongoDB.
 */
public interface ReservationRepository extends MongoRepository<Reservation, ObjectId> {

     /**
     * Busca una reserva por su ID.
     *
     * @param id Identificador único de la reserva.
     * @return Optional que contiene la reserva si se encuentra, o vacío si no existe.
     */
    Optional<Reservation> findById(ObjectId id);


    /**
     * Busca reservas que coincidan con la fecha y el rango de tiempo.
     *
     * @param date        Fecha de la reserva.
     * @param initialTime Hora inicial de la reserva.
     * @param finalTime   Hora final de la reserva.
     * @return Lista de reservas que coinciden con el criterio.
     */
    List<Reservation> findByDateAndInitialTimeLessThanEqualAndFinalTimeGreaterThanEqual(
            Date date, String finalTime, String initialTime);
}
