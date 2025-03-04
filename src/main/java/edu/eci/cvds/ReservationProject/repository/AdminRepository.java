package edu.eci.cvds.ReservationProject.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.cvds.ReservationProject.model.Admin;
    
    /**
 * Repositorio para gestionar las operaciones CRUD de los administradores en la
 * base de datos MongoDB.
 */
public interface AdminRepository extends MongoRepository<Admin, ObjectId> {

    /**
     * Busca un admin por su ID.
     *
     * @param id Identificador único del administrador.
     * @return Optional que contiene el administrador si se encuentra, o vacío si no
     *         existe.
     */
    Optional<Admin> findById(ObjectId id);

    /**
     * Busca un administrador por su nombre.
     *
     * @param name Nombre del admin.
     * @return Optional que contiene el administrador si se encuentra, o vacío si no
     *         existe.
     */
    Optional<Admin> findByName(String name);

    /**
     * Busca un administrador por su email.
     *
     * @param email Email del admin.
     * @return Optional que contiene el administrador si se encuentra, o vacío si no
     *         existe.
     */
    Optional<Admin> findByEmail(String email);
}
