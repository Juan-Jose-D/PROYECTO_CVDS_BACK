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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class serviceTest {
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
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private Random random;
    private Laboratory laboratory;
    private Reservation reservation;
    private User user;
    private ObjectId id;
    private LocalDate date;
    private LocalTime time;

    @BeforeEach
    void setUp() {
        id = new ObjectId();
        laboratory = new Laboratory(new ObjectId(), "AI Lab", 30, "Building A", "Advanced AI research", true, "PCs, GPUs");
        user = new User(new ObjectId(), "John Doe", "john.doe@example.com", "password123", "USER");
        reservation = new Reservation(new ObjectId(), "John Doe", null, "10:00", "12:00", true, "Research", 5, laboratory);
        date = LocalDate.of(2025, 3, 10);
        time = LocalTime.of(10, 0);
    }


    @Test
    void testGetReservation_WhenNoReservationExists_ThenCreateReservation() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        List<Reservation> resultList = reservationService.getAllReservations();
        assertTrue(resultList.isEmpty());

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        Reservation resultCreate = reservationService.createReservation(reservation);
        assertNotNull(resultCreate);
        assertEquals(reservation.getId(), resultCreate.getId());

        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        Reservation resultGet = reservationService.getReservationById(id);
        assertNotNull(resultGet);
        assertEquals(reservation.getId(), resultGet.getId());
    }

    @Test
    void shouldDeleteReservation_GivenReservationExists() {
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        assertNotNull(reservationService.getReservationById(id));

        doNothing().when(reservationRepository).deleteById(id);
        assertDoesNotThrow(() -> reservationService.deleteReservation(id));
    }


    @Test
    void givenReservationExists_ThenDeleteAndVerifyDeletion() {
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        assertNotNull(reservationService.getReservationById(id));

        doNothing().when(reservationRepository).deleteById(id);
        assertDoesNotThrow(() -> reservationService.deleteReservation(id));

        verify(reservationRepository, times(1)).deleteById(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ReservationProjectException.class, () -> reservationService.getReservationById(id));
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
    void testCreateRandomReservationsInRange() {

        LaboratoryService laboratoryService2 = mock(LaboratoryService.class);
        ReservationRepository reservationRepository2 = mock(ReservationRepository.class);
        ReservationService reservationService2 = new ReservationService(reservationRepository2, laboratoryService2);

        Random randomMock = mock(Random.class);
        reservationService2.createRandomReservationsInRange(randomMock);

        verify(reservationRepository2, atLeast(100)).save(any(Reservation.class));
        verify(reservationRepository2, atMost(1000)).save(any(Reservation.class));
    }

}
