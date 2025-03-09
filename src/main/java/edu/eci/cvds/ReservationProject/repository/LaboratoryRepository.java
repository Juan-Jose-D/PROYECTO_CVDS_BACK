package edu.eci.cvds.ReservationProject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.model.Reservation;

/**
 * Repositorio para gestionar las operaciones CRUD de los laboratorios en la
 * base de datos MongoDB.
 */
public interface LaboratoryRepository extends MongoRepository<Laboratory, ObjectId> {

    /**
     * Busca un laboratorio por su ID.
     *
     * @param id Identificador único del laboratorio.
     * @return Optional que contiene el laboratorio si se encuentra, o vacío si no
     *         existe.
     */
    Optional<Laboratory> findById(ObjectId id);

    /**
     * Busca un laboratorio por su nombre.
     *
     * @param name Nombre del laboratorio.
     * @return Optional que contiene el laboratorio si se encuentra, o vacío si no
     *         existe.
     */
    Optional<Laboratory> findByName(String name);

    /**
     * Busca laboratorios por su estado de disponibilidad.
     *
     * @param available Estado de disponibilidad del laboratorio.
     * @return Lista de laboratorios con el estado de disponibilidad especificado.
     */
    List<Laboratory> findByAvailable(Boolean available);

    /**
     * Busca laboratorios por capacidad mínima.
     *
     * @param capacity Capacidad mínima requerida.
     * @return Lista de laboratorios que tienen al menos la capacidad especificada.
     */
    List<Laboratory> findByCapacityGreaterThanEqual(int capacity);

    /**
     * Busca laboratorios por ubicación.
     *
     * @param location Ubicación del laboratorio.
     * @return Lista de laboratorios en la ubicación especificada.
     */
    List<Laboratory> findByLocation(String location);

}
