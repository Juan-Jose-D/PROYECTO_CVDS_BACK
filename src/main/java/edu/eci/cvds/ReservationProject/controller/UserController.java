package edu.eci.cvds.ReservationProject.controller;

import edu.eci.cvds.ReservationProject.model.User;
import edu.eci.cvds.ReservationProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
    origins = {
        "https://frontdespliegue-gae9f9b2aaedfabw.eastus-01.azurewebsites.net", 
        "http://localhost:3000", 
        "http://localhost:5500"
    }, 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)

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
     * @param user Usuario a crear.
     * @return El usuario creado con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            if (user.getRole() == null) {
                user.setRole("USER");
            }
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Crea un nuevo administrador.
     *
     * @param user Usuario a crear como administrador.
     * @return El administrador creado con estado HTTP 201 (CREATED).
     */
    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        try {
            user.setRole("ADMIN");
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
     * @param id ID del usuario.
     * @return El usuario encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email Email del usuario.
     * @return El usuario encontrado.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email).orElse(null));
    }

    /**
     * Obtiene todos los administradores.
     *
     * @return Lista de administradores.
     */
    @GetMapping("/admins")
    public List<User> getAllAdmins() {
        return userService.getUsersByRole("ADMIN");
    }
}