package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.User;
import edu.eci.cvds.ReservationProject.model.Admin;
import edu.eci.cvds.ReservationProject.repository.UserRepository;
import edu.eci.cvds.ReservationProject.repository.AdminRepository;
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
    private final AdminRepository adminRepository;

    @Autowired
    public UserService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
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


    public Object authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin.get();
        } else if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }

        return null;
    }

}
