package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.model.User;
import edu.eci.cvds.ReservationProject.repository.LaboratoryRepository;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import edu.eci.cvds.ReservationProject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


/**
 * Servicio para gestionar las reservas en la aplicación.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea una nueva reserva y la guarda en la base de datos.
     * Valida que no haya conflictos de horario.
     *
     * @param user El usuario a crear.
     * @return La reserva guardada.
     */
    public User createUser(User user) {
        user.setId(new ObjectId());
        return userRepository.save(user);
    }

    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de reservas.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id Identificador de la reserva.
     * @return La reserva encontrada o una excepción si no existe.
     */
    public User getUserById(ObjectId id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.USER_NOT_FOUND));
    }

    public User authenticate(String email, String password) {
        // Buscar al usuario por el correo electrónico
        User user = userRepository.findByEmail(email);
        
        // Si el usuario existe y la contraseña coincide, lo devolvemos
        if (user != null && user.getPassword().equals(password)) {
            return user; // Devolvemos el usuario con el tipo de usuario (admin o user)
        }

        return null; // Si no se encontró o la contraseña no es correcta
    }

}
