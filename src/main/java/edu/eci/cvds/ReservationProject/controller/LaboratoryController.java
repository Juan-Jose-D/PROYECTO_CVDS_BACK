package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.service.LaboratoryService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(
    origins = {
        "https://frontdespliegue-gae9f9b2aaedfabw.eastus-01.azurewebsites.net",
        "https://proyectoreservascvdsfrontend-fxgqetgfcsagbqhf.eastus-01.azurewebsites.net",
        "http://localhost:3000", 
        "http://localhost:5500"
    }, 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
/**
 * Controlador REST para gestionar los laboratorios.
 */
@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;


    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    /**
     * Crea un nuevo laboratorio.
     *
     * @param laboratory Laboratorio a crear.
     * @return El laboratorio creado con estado HTTP 201 (CREATED).
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



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable String id) {
        try {
            ObjectId laboratoryId = new ObjectId(id);
            laboratoryService.deleteLaboratory(laboratoryId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
}

}   
