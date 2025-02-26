package edu.eci.cvds.ReservationProject.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import edu.eci.cvds.ReservationProject.model.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, ObjectId> {
    Optional<Reservation> findById(ObjectId id);
}
