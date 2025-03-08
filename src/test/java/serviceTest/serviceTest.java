package serviceTest;

import edu.eci.cvds.ReservationProject.ReservationProjectException;
import edu.eci.cvds.ReservationProject.model.*;
import edu.eci.cvds.ReservationProject.repository.*;
import edu.eci.cvds.ReservationProject.service.*;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class serviceTest {

    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminService adminService;

    @Mock
    private LaboratoryRepository laboratoryRepository;
    @InjectMocks
    private LaboratoryService laboratoryService;

    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private Admin admin;
    private Laboratory laboratory;
    private Reservation reservation;
    private User user;
    private ObjectId id;

    @BeforeEach
    void setUp() {
        id = new ObjectId();
        admin = new Admin(new ObjectId(), "Jessica", "jessica@bene-gesserit.com", "litany123");
        laboratory = new Laboratory(new ObjectId(), "AI Lab", 30, "Building A", "Advanced AI research", true, "PCs, GPUs");
        user = new User(new ObjectId(), "John Doe", "john.doe@example.com", "password123");
        reservation = new Reservation(new ObjectId(), "John Doe", null, "10:00", "12:00", true, "Research", laboratory);
    }

    @Test
    void shouldCreateAdmin() {
        when(adminRepository.save(admin)).thenReturn(admin);
        Admin result = adminService.createAdmin(admin);
        assertNotNull(result);
        assertEquals(admin.getName(), result.getName());
    }

    @Test
    void shouldGetAllAdmins() {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(admin));
        List<Admin> result = adminService.getAllAdmins();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetAdminById() {
        when(adminRepository.findById(id)).thenReturn(Optional.of(admin));
        Admin result = adminService.getAdminById(id);
        assertNotNull(result);
    }

    @Test
    void shouldGetAdminByName() {
        when(adminRepository.findByName(admin.getName())).thenReturn(Optional.of(admin));
        Admin result = adminService.getAdminByName(admin.getName());
        assertNotNull(result);
    }

    @Test
    void shouldGetAdminByEmail() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        Admin result = adminService.getAdminByEmail(admin.getEmail());
        assertNotNull(result);
    }

    @Test
    void shouldCreateLaboratory() {
        when(laboratoryRepository.save(laboratory)).thenReturn(laboratory);
        Laboratory result = laboratoryService.createLaboratory(laboratory);
        assertNotNull(result);
    }

    @Test
    void shouldGetAllLaboratories() {
        when(laboratoryRepository.findAll()).thenReturn(Collections.singletonList(laboratory));
        List<Laboratory> result = laboratoryService.getAllLaboratories();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetLaboratoryById() {
        when(laboratoryRepository.findById(id)).thenReturn(Optional.of(laboratory));
        Laboratory result = laboratoryService.getLaboratoryById(id);
        assertNotNull(result);
    }

    @Test
    void shouldCreateReservation() {
        when(reservationRepository.findByDateAndInitialTimeLessThanEqualAndFinalTimeGreaterThanEqualAndLaboratory(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation result = reservationService.createReservation(reservation);
        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionForConflictingReservation() {
        when(reservationRepository.findByDateAndInitialTimeLessThanEqualAndFinalTimeGreaterThanEqualAndLaboratory(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(reservation));
        assertThrows(ReservationProjectException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void shouldGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservation));
        List<Reservation> result = reservationService.getAllReservations();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetReservationById() {
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        Reservation result = reservationService.getReservationById(id);
        assertNotNull(result);
    }

    @Test
    void shouldUpdateReservation() {
        when(reservationRepository.existsById(id)).thenReturn(true);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation result = reservationService.updateReservation(id, reservation);
        assertNotNull(result);
    }

    @Test
    void shouldDeleteReservation() {
        doNothing().when(reservationRepository).deleteById(id);
        assertDoesNotThrow(() -> reservationService.deleteReservation(id));
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.createUser(user);
        assertNotNull(result);
    }

    @Test
    void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> result = userService.getAllUsers();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User result = userService.getUserById(id);
        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenAdminByEmailNotFound() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.empty());
        assertThrows(ReservationProjectException.class, () -> adminService.getAdminByEmail(admin.getEmail()));
    }

    @Test
    void shouldThrowExceptionWhenAdminByNameNotFound() {
        when(adminRepository.findByName(admin.getName())).thenReturn(Optional.empty());
        assertThrows(ReservationProjectException.class, () -> adminService.getAdminByName(admin.getName()));
    }

    @Test
    void shouldThrowExceptionWhenAdminByIdNotFound() {
        when(adminRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ReservationProjectException.class, () -> adminService.getAdminById(id));
    }

    @Test
    void shouldAuthenticateAsAdmin() {
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        Object result = userService.authenticate(admin.getEmail(), admin.getPassword());
        assertNotNull(result);
        assertTrue(result instanceof Admin);
        assertEquals(admin.getEmail(), ((Admin) result).getEmail());
    }

    @Test
    void shouldAuthenticateAsUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Object result = userService.authenticate(user.getEmail(), user.getPassword());
        assertNotNull(result);
        assertTrue(result instanceof User);
        assertEquals(user.getEmail(), ((User) result).getEmail());
    }

    @Test
    void shouldReturnNullForInvalidCredentials() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Object result = userService.authenticate(user.getEmail(), "wrongPassword");
        assertNull(result);
    }

    @Test
    void shouldReturnNullForNonExistentEmail() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        when(adminRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        Object result = userService.authenticate("nonexistent@example.com", "password123");
        assertNull(result);
    }

}
