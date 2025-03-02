package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.service.ReservationService;
import edu.eci.cvds.ReservationProject.service.LaboratoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las reservas.
 */
@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;


    @Autowired
    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    /**
     * Crea una nueva reserva.
     *
     * @param reservation Reserva a crear.
     * @return La reserva creada con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> createLaboratory(@RequestBody Laboratory laboratory) {
        try {
            return new ResponseEntity<>(laboratoryService.createLaboratory(laboratory), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Obtiene todos los laboratorios.
     *
     * @return Lista de reservas.
     */
    @GetMapping
    public List<Laboratory> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    /**
     * Obtiene un laboratorio por su ID.
     *
     * @param id ID del laboratorio.
     * @return El laboratorio encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Laboratory> getLaboratory(@PathVariable ObjectId id) {
        return ResponseEntity.ok(laboratoryService.getLaboratoryById(id));
    }

}
