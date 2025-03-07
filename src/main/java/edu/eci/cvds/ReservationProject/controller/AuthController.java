package edu.eci.cvds.ReservationProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(true, user.getUserType()));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Credenciales incorrectas"));
        }
    }
}
