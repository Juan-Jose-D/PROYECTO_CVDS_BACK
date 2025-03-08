package edu.eci.cvds.ReservationProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.eci.cvds.ReservationProject.model.LoginRequest;
import edu.eci.cvds.ReservationProject.model.LoginResponse;
import edu.eci.cvds.ReservationProject.service.UserService;



@RestController
@RequestMapping("/login")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Object user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(true, "Credenciales correctas"));    
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Credenciales incorrectas"));
        }
    }
}
