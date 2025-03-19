package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.User;
import edu.eci.cvds.ReservationProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param user Usuario a crear.
     * @return El usuario creado.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String getUserRole(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return El usuario encontrado.
     */
    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email Email del usuario.
     * @return El usuario encontrado.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Obtiene todos los usuarios con un rol específico.
     *
     * @param role Rol de los usuarios a buscar.
     * @return Lista de usuarios con el rol especificado.
     */
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}




