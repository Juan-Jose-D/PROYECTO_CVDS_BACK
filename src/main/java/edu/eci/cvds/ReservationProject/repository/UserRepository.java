package edu.eci.cvds.ReservationProject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import edu.eci.cvds.ReservationProject.model.User;

/**
 * Repositorio para gestionar las operaciones CRUD de los laboratorios en la
 * base de datos MongoDB.
 */
public interface UserRepository extends MongoRepository<User, ObjectId> {

    /**
     * Busca un usuario por su ID.
     *
     * @param id Identificador único del usuario.
     * @return Optional que contiene el usuario si se encuentra, o vacío si no
     *         existe.
     */
    Optional<User> findById(ObjectId id);

    /**
     * Busca un usuario por su nombre.
     *
     * @param name Nombre del usuario.
     * @return Optional que contiene el usuario si se encuentra, o vacío si no
     *         existe.
     */
    Optional<User> findByName(String name);

    /**
     * Busca usuarios por su email.
     *
     * @param email correo electronico del usuario.
     * @return Lista de usuarios con el correo electronico.
     */
    Optional<User> findByEmail(String email);

}
