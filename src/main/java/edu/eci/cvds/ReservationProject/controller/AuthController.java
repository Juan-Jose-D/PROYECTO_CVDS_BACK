package edu.eci.cvds.ReservationProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import edu.eci.cvds.ReservationProject.config.JwtUtil;
import edu.eci.cvds.ReservationProject.model.LoginRequest;
import edu.eci.cvds.ReservationProject.model.LoginResponse;
import edu.eci.cvds.ReservationProject.service.UserDetailsServiceImpl;

@CrossOrigin(
    origins = {
        "https://frontdespliegue-gae9f9b2aaedfabw.eastus-01.azurewebsites.net",
        "https://proyectoreservascvdsfrontend-fxgqetgfcsagbqhf.eastus-01.azurewebsites.net", 
        "http://localhost:3000", 
        "http://localhost:5500"
    }, 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            String userRole = userDetails.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles"))
                .getAuthority();
            return ResponseEntity.ok(new LoginResponse(true, userRole, token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Credenciales incorrectas", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new LoginResponse(false, "Error interno del servidor", null));
        }
    }
}