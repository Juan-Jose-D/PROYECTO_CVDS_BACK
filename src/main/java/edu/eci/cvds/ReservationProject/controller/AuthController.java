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
        boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated) {
            String userRole = userService.getUserRole(loginRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse(true, userRole));    
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Credenciales incorrectas"));
        }
    }
}