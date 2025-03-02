package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.repository.LaboratoryRepository;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


/**
 * Servicio para gestionar las reservas en la aplicación.
 */
@Service
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    @Autowired
    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * Crea una nueva reserva y la guarda en la base de datos.
     * Valida que no haya conflictos de horario.
     *
     * @param reservation La reserva a crear.
     * @return La reserva guardada.
     */
    public Laboratory createLaboratory(Laboratory laboratory) {

        laboratory.setId(new ObjectId());
        return laboratoryRepository.save(laboratory);
    }

    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de reservas.
     */
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id Identificador de la reserva.
     * @return La reserva encontrada o una excepción si no existe.
     */
    public Laboratory getLaboratoryById(ObjectId id) {
        return laboratoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratorio no encontrado con id: " + id));
    }

}
