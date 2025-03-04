package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.repository.LaboratoryRepository;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


/**
 * Servicio para gestionar los laboratorios en la aplicación.
 */
@Service
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    @Autowired
    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * Crea un nuevo laboratorio y lo guarda en la base de datos.
     *
     * @param laboratory El laboratorio a crear.
     * @return El laboratorio guardado.
     */
    public Laboratory createLaboratory(Laboratory laboratory) {

        laboratory.setId(new ObjectId());
        return laboratoryRepository.save(laboratory);
    }

    /**
     * Obtiene todos los laboratorios registrados
     *
     * @return Lista de laboratorios
     */
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Busca un laboratorio por su ID.
     *
     * @param id Identificador del laboratorio.
     * @return El laboratorio encontrado o una excepción si no existe.
     */
    public Laboratory getLaboratoryById(ObjectId id) {
        return laboratoryRepository.findById(id)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.LABORATORY_NOT_FOUND + id));
    }

}
