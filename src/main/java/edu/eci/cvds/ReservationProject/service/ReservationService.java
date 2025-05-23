package edu.eci.cvds.ReservationProject.service;


import edu.eci.cvds.ReservationProject.model.Laboratory;
import edu.eci.cvds.ReservationProject.model.Reservation;
import edu.eci.cvds.ReservationProject.repository.ReservationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Random;

import edu.eci.cvds.ReservationProject.ReservationProjectException;

/**
 * Servicio para gestionar las reservas en la aplicación.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LaboratoryService laboratoryService;
    private Random random = new Random();

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, LaboratoryService laboratoryService) {
        this.reservationRepository = reservationRepository;
        this.laboratoryService = laboratoryService;
    }

    /**
     * Crea una nueva reserva y la guarda en la base de datos.
     * Valida que no haya conflictos de horario.
     *
     * @param reservation La reserva a crear.
     * @return La reserva guardada.
     */
    public Reservation createReservation(Reservation reservation) {
        List<Reservation> existingReservations = reservationRepository.findByDateAndInitialTimeLessThanEqualAndFinalTimeGreaterThanEqualAndLaboratory(
                reservation.getDate(),
                reservation.getInitialTime(),
                reservation.getFinalTime(),
                reservation.getLaboratory()
        );

        if (!existingReservations.isEmpty()) {
            throw new ReservationProjectException(ReservationProjectException.LABORATORY_RESERVED);
        }

        reservation.setId(new ObjectId());
        reservation.setStatus(true);
        return reservationRepository.save(reservation);
    }


    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de reservas.
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id Identificador de la reserva.
     * @return La reserva encontrada o una excepción si no existe.
     */
    public Reservation getReservationById(ObjectId id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.RESERVATION_NOT_FOUND + id));
    }


    public void deleteReservationByLabDateAndTime(ObjectId laboratoryId, Date date, String initialTime) {
        Laboratory lab = laboratoryService.getLaboratoryById(laboratoryId);
        Reservation reservation = reservationRepository.findByLaboratoryAndDateAndInitialTime(lab, date, initialTime)
                .orElseThrow(() -> new ReservationProjectException(ReservationProjectException.RESERVATION_NOT_FOUND + laboratoryId));
        
        reservationRepository.deleteById(reservation.getId());
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id Identificador de la reserva a eliminar.
     */
    public void deleteReservation(ObjectId id) {
        reservationRepository.deleteById(id);
    }


    /**
     * Modifica una reserva existente.
     *
     * @param id          Identificador de la reserva a modificar.
     * @param reservation Datos actualizados de la reserva.
     * @return La reserva modificada.
     */
    public Reservation updateReservation(ObjectId id, Reservation reservation) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationProjectException(ReservationProjectException.RESERVATION_NOT_FOUND + id);
        }
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

        /**
     * Verifica si un laboratorio está disponible para una fecha y hora específica.
     * 
     * @param laboratoryId ID del laboratorio a verificar.
     * @param date Fecha para la consulta.
     * @param time Hora para la consulta.
     * @return true si el laboratorio está disponible, false si no.
     */
    public boolean isLaboratoryAvailable(ObjectId laboratoryId, LocalDate date, LocalTime time) {

        if (laboratoryId == null || date == null || time == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos.");
        }

        Date queryDate = Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
        List<Reservation> reservations = reservationRepository.findByLaboratoryIdAndDate(laboratoryId, queryDate);

        if (reservations.isEmpty()) {
            return true;
        }

        for (Reservation reservation : reservations) {
            try {
                LocalTime reservationStart = LocalTime.parse(reservation.getInitialTime());
                LocalTime reservationEnd = LocalTime.parse(reservation.getFinalTime());

                if ((time.isAfter(reservationStart) || time.equals(reservationStart))
                    && (time.isBefore(reservationEnd) || time.equals(reservationEnd))) {
                    return false;
            }
            } catch (DateTimeParseException e) {
                System.err.println("Error al parsear la hora: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    /**
     * Genera "proceduralmente" nuevas reservas, aleatoriamente serán entre 100 y
     * 1000 (Corresponden a las reservas de los laboratorios).
     */
    public void createRandomReservationsInRange(Random random){
        int numberOfReservations = random.nextInt(901) + 100;

        for (int i = 0; i < numberOfReservations; i++) {
            Reservation reservation = new Reservation();
            reservation.setId(new ObjectId());
            reservation.setLaboratory(getRandomLaboratory(random));
            reservation.setDate(getRandomDate(random));
            reservation.setInitialTime(getRandomTime(random));
            reservation.setFinalTime(getRandomTimeAfter(reservation.getInitialTime(), random));
            reservation.setPriority(random.nextInt(5) + 1);
            reservation.setStatus(true);
            reservationRepository.save(reservation);
        }
    }

    /**
     * Obtiene aleatoriamente un laboratorio de la lista de laboratorios disponibles.
     *
     * @param random Instancia de Random para la selección aleatoria.
     * @return Un laboratorio aleatorio, o {@code null} si no hay laboratorios disponibles.
     */
    private Laboratory getRandomLaboratory(Random random) {
        List<Laboratory> labs = laboratoryService.getAllLaboratories();
        return labs.isEmpty() ? null : labs.get(random.nextInt(labs.size()));
    }

    /**
     * Genera una fecha aleatoria dentro de los próximos 30 días a partir de la fecha actual.
     *
     * @param random Instancia de Random para la generación aleatoria.
     * @return Un objeto {@code Date} representando la fecha generada.
     */
    private Date getRandomDate(Random random) {
        LocalDate randomDate = LocalDate.now().plusDays(random.nextInt(30));
        return Date.from(randomDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    /**
     * Genera una hora aleatoria dentro del rango de 07:00 a 15:59.
     *
     * @param random Instancia de Random para la generación aleatoria.
     * @return Hora generada en formato {@code "HH:mm"}.
     */
    private String getRandomTime(Random random) {
        int hour = random.nextInt(9) + 7;
        int minute = random.nextInt(60);
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Genera una hora de finalización aleatoria después de la hora inicial dada.
     * El tiempo extra estará en un rango de 90 a 270 minutos.
     *
     * @param initialTime Hora inicial en formato {@code "HH:mm"}.
     * @param random Instancia de Random para la generación aleatoria.
     * @return Una nueva hora en formato {@code "HH:mm"}, o "12:00" en caso de error en el formato.
     */
    private String getRandomTimeAfter(String initialTime, Random random) {
        try {
            LocalTime start = LocalTime.parse(initialTime);
            int extraMinutes = random.nextInt(181) + 90;
            return start.plusMinutes(extraMinutes).toString();
        } catch (DateTimeParseException e) {
            return "12:00";
        }
    }


}
