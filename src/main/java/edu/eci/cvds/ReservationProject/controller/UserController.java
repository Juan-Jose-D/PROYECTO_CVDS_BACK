package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.model.User;
import edu.eci.cvds.ReservationProject.service.ReservationService;
import edu.eci.cvds.ReservationProject.service.LaboratoryService;
import edu.eci.cvds.ReservationProject.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param reservation Reserva a crear.
     * @return La reserva creada con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del laboratorio.
     * @return El laboratorio encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUseryById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }



}
