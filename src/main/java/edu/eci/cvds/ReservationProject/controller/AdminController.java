package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.Admin;
import edu.eci.cvds.ReservationProject.service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los administradores.
 */
@RestController
@RequestMapping("/administrators")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Crea un nuevo administrador.
     *
     * @param admin Adminitrador a crear.
     * @return El administrador creado con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        try {
            return new ResponseEntity<>(adminService.createAdmin(admin), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Obtiene todos los administradores.
     *
     * @return Lista de administradores.
     */
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    /**
     * Obtiene un administrador por su ID.
     *
     * @param id ID del administrador.
     * @return El administrador encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getLaboratory(@PathVariable ObjectId id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    /**
     * Obtiene un administrador por su Email.
     *
     * @param email Email del administrador.
     * @return El administrador encontrado.
     */
    @GetMapping("/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    /**
     * Obtiene un administrador por su nombre.
     *
     * @param name Nombre del administrador.
     * @return El administrador encontrado.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Admin> getAdminByName(@PathVariable String name) {
        return ResponseEntity.ok(adminService.getAdminByEmail(name));
    }

}
