package edu.eci.cvds.ReservationProject.modelTest;

// Importacion de paquetes
import edu.eci.cvds.ReservationProject.model.*;

// Importacion de librerias

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;



import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

@SpringBootTest
class modelTest {


    // Model tests

    @Test
    public void testUsersGetterAndSetter() {
        ObjectId id = new ObjectId();
        User user = new User();

        user.setId(id);
        user.setName("Paul Atreides");
        user.setEmail("paul.atreides@atreides.com");
        user.setPassword("password123");

        assertEquals(id, user.getId());
        assertEquals("Paul Atreides", user.getName());
        assertEquals("paul.atreides@atreides.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testUserConstructor() {
        ObjectId id = new ObjectId();
        User user = new User(id, "Duncan Idaho", "duncan.idaho@atreides.com", "swordmaster");

        assertEquals(id, user.getId());
        assertEquals("Duncan Idaho", user.getName());
        assertEquals("duncan.idaho@atreides.com", user.getEmail());
        assertEquals("swordmaster", user.getPassword());
    }

    @Test
    public void testAdminGetterAndSetter() {
        ObjectId id = new ObjectId();
        Admin admin = new Admin(id, "Jessica Atreides", "jessica@bene-gesserit.com", "litany123");

        assertEquals(id, admin.getId());
        assertEquals("Jessica Atreides", admin.getName());
        assertEquals("jessica@bene-gesserit.com", admin.getEmail());
        assertEquals("litany123", admin.getPassword());

        admin.setName("Duke Leto");
        admin.setEmail("duke.leto@atreides.com");
        admin.setPassword("arrakis");

        assertEquals("Duke Leto", admin.getName());
        assertEquals("duke.leto@atreides.com", admin.getEmail());
        assertEquals("arrakis", admin.getPassword());
    }

    @Test
    public void testLaboratoryGetterAndSetter() {
        ObjectId id = new ObjectId();
        Laboratory lab = new Laboratory(id, "Lab Fremen", 30, "Arrakis 101", "Lab especializado en IA", true, "PCs y VR");

        assertEquals(id, lab.getId());
        assertEquals("Lab Fremen", lab.getName());
        assertEquals(30, lab.getCapacity());
        assertEquals("Arrakis 101", lab.getLocation());
        assertEquals("Lab especializado en IA", lab.getDescription());
        assertTrue(lab.getAvailable());
        assertEquals("PCs y VR", lab.getEquipmentDetails());

        lab.setName("Lab Mentat");
        lab.setCapacity(50);
        lab.setLocation("Caladan 202");
        lab.setDescription("Lab para análisis de datos");
        lab.setAvailable(false);
        lab.setEquipmentDetails("Supercomputadoras");

        assertEquals("Lab Mentat", lab.getName());
        assertEquals(50, lab.getCapacity());
        assertEquals("Caladan 202", lab.getLocation());
        assertEquals("Lab para análisis de datos", lab.getDescription());
        assertFalse(lab.getAvailable());
        assertEquals("Supercomputadoras", lab.getEquipmentDetails());
    }

    @Test
    public void testReservationGetterAndSetter() {
        ObjectId id = new ObjectId();
        Date date = new Date();
        Laboratory lab = new Laboratory(new ObjectId(), "Lab Harkonnen", 20, "Giedi Prime", "Hacking y ciberseguridad", true, "Redes y servidores");

        Reservation res = new Reservation(id, "Rabban Harkonnen", date, "08:00", "10:00", true, "Investigación", lab);

        assertEquals(id, res.getId());
        assertEquals("Rabban Harkonnen", res.getUser());
        assertEquals(date, res.getDate());
        assertEquals("08:00", res.getInitialTime());
        assertEquals("10:00", res.getFinalTime());
        assertTrue(res.getStatus());
        assertEquals("Investigación", res.getPurpose());
        assertEquals(lab, res.getLaboratory());

        res.setUser("Feyd-Rautha");
        res.setDate(new Date());
        res.setInitialTime("14:00");
        res.setFinalTime("16:00");
        res.setStatus(false);
        res.setPurpose("Defensa de tesis");
        res.setLaboratory(new Laboratory(new ObjectId(), "Lab Ix", 40, "Ix 303", "Experimentación avanzada", true, "Equipos de simulación"));

        assertEquals("Feyd-Rautha", res.getUser());
        assertEquals("14:00", res.getInitialTime());
        assertEquals("16:00", res.getFinalTime());
        assertFalse(res.getStatus());
        assertEquals("Defensa de tesis", res.getPurpose());
        assertNotNull(res.getLaboratory());
    }


    // Controller tests



}
