package edu.eci.cvds.ReservationProject.service;

import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.Admin;
import edu.eci.cvds.ReservationProject.repository.AdminRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



/**
 * Servicio para gestionar los administradores en la aplicación.
 */
@Service
public class AdminService {
    
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Crea un nuevo administrador y lo guarda en la base de datos.
     *
     * @param admin El administrador a crear.
     * @return El admin guardado.
     */
    public Admin createAdmin(Admin admin) {

        admin.setId(new ObjectId());
        return adminRepository.save(admin);
    }

    /**
     * Obtiene todos los administradores registrados
     *
     * @return Lista de admins.
     */
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    /**
     * Busca un admin por su ID.
     *
     * @param id Identificador del admin.
     * @return El admin encontrado o una excepción si no existe.
     */
    public Admin getAdminById(ObjectId id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.ADMIN_NOT_FOUND));
    }


    /**
     * Busca un admin por su nombre.
     *
     * @param name Nombre del admin.
     * @return El admin encontrado o una excepción si no existe.
     */
    public Admin getAdminByName(String name) {
        return adminRepository.findByName(name)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.ADMIN_NOT_FOUND));
    }
    

    /**
     * Busca un admin por su Email.
     *
     * @param email Email del admin.
     * @return El admin encontrado o una excepción si no existe.
     */
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.ADMIN_NOT_FOUND));
    }
}

