package controllerTest;

import edu.eci.cvds.ReservationProject.model.*;
import edu.eci.cvds.ReservationProject.service.*;
import edu.eci.cvds.ReservationProject.controller.*;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class controllerTest {

    @Mock
    private LaboratoryService laboratoryService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LaboratoryController laboratoryController;

    @InjectMocks
    private ReservationController reservationController;

    @InjectMocks
    private UserController userController;

    private ObjectId testLabId;
    private Laboratory testLab;
    private Reservation testReservation;
    private User testUser;

    @BeforeEach
    void setUp() {
        testLabId = new ObjectId();
        testLab = new Laboratory(new ObjectId(), "AI Lab", 30, "Building A", "Advanced AI research", true, "PCs, GPUs");
        testReservation = new Reservation(new ObjectId(), "John Doe", null, "10:00", "12:00", true, "Research", 5, testLab);
        testUser = new User(new ObjectId(), "John Doe", "john.doe@example.com", "password123", "USER");
    }

    // UserController Tests
    @Test
    void testCreateUser_Success() {
        when(userService.createUser(any(User.class))).thenReturn(testUser);
        ResponseEntity<?> response = userController.createUser(testUser);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testCreateUser_Conflict() {
        when(userService.createUser(any(User.class)))
                .thenThrow(new RuntimeException("User already exists"));

        ResponseEntity<?> response = userController.createUser(testUser);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void testCreateAdmin_Success() {
        User adminUser = new User(new ObjectId(), "Admin User", "admin@example.com", "admin123", "ADMIN");
        when(userService.createUser(any(User.class))).thenReturn(adminUser);

        ResponseEntity<?> response = userController.createAdmin(adminUser);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(adminUser, response.getBody());
    }

    @Test
    void testCreateAdmin_Conflict() {
        User adminUser = new User(new ObjectId(), "Admin User", "admin@example.com", "admin123", "ADMIN");
        when(userService.createUser(any(User.class)))
                .thenThrow(new RuntimeException("User already exists"));

        ResponseEntity<?> response = userController.createAdmin(adminUser);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(testUser));
        List<User> users = userController.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(any())).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserById(testUser.getId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testGetUserByEmail() {
        when(userService.getUserByEmail(any())).thenReturn(Optional.of(testUser));
        ResponseEntity<User> response = userController.getUserByEmail(testUser.getUsername());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testGetAllAdmins() {
        User adminUser = new User(new ObjectId(), "Admin User", "admin@example.com", "admin123", "ADMIN");
        when(userService.getUsersByRole("ADMIN")).thenReturn(Arrays.asList(adminUser));

        List<User> admins = userController.getAllAdmins();
        assertEquals(1, admins.size());
        assertEquals("Admin User", admins.get(0).getName());
    }

    // LaboratoryController Tests
    @Test
    void testCreateLaboratory_Success() {
        when(laboratoryService.createLaboratory(any(Laboratory.class))).thenReturn(testLab);
        ResponseEntity<?> response = laboratoryController.createLaboratory(testLab);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(testLab, response.getBody());
    }

    @Test
    void testCreateLaboratory_Conflict() {
        when(laboratoryService.createLaboratory(any(Laboratory.class)))
                .thenThrow(new RuntimeException("Laboratory already exists"));

        ResponseEntity<?> response = laboratoryController.createLaboratory(testLab);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Laboratory already exists", response.getBody());
    }

    @Test
    void testGetAllLaboratories() {
        when(laboratoryService.getAllLaboratories()).thenReturn(Arrays.asList(testLab));
        List<Laboratory> labs = laboratoryController.getAllLaboratories();
        assertEquals(1, labs.size());
        assertEquals("AI Lab", labs.get(0).getName());
    }

    @Test
    void testGetLaboratoryById() {
        when(laboratoryService.getLaboratoryById(any())).thenReturn(testLab);
        ResponseEntity<Laboratory> response = laboratoryController.getLaboratory(testLab.getId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testLab, response.getBody());
    }

    // ReservationController Tests
    @Test
    void testCreateReservation_Success() {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(testReservation);
        ResponseEntity<?> response = reservationController.createReservation(testReservation);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(testReservation, response.getBody());
    }

    @Test
    void testCreateReservation_Conflict() {
        when(reservationService.createReservation(any(Reservation.class)))
                .thenThrow(new RuntimeException("Reservation already exists"));

        ResponseEntity<?> response = reservationController.createReservation(testReservation);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Reservation already exists", response.getBody());
    }

    @Test
    void testGetAllReservations() {
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(testReservation));
        List<Reservation> reservations = reservationController.getAllReservations();
        assertEquals(1, reservations.size());
        assertEquals("John Doe", reservations.get(0).getUser());
    }

    @Test
    void testDeleteReservation() {
        doNothing().when(reservationService).deleteReservation(any());
        ResponseEntity<Void> response = reservationController.deleteReservation(testReservation.getId());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetReservation_Success() {
        ObjectId id = new ObjectId();
        when(reservationService.getReservationById(id)).thenReturn(testReservation);

        ResponseEntity<Reservation> response = reservationController.getReservation(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testReservation, response.getBody());
    }

    @Test
    void testGetReservation_NotFound() {
        ObjectId id = new ObjectId();
        when(reservationService.getReservationById(id)).thenThrow(new RuntimeException("Reservation not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationController.getReservation(id);
        });

        assertEquals("Reservation not found", exception.getMessage());
    }
}