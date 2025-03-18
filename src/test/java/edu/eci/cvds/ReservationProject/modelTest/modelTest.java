package edu.eci.cvds.ReservationProject.modelTest;

import edu.eci.cvds.ReservationProject.model.*;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class modelTest {

    @Test
    public void testUsersGetterAndSetter() {
        ObjectId id = new ObjectId();
        User user = new User();

        user.setId(id);
        user.setName("Paul Atreides");
        user.setEmail("paul.atreides@atreides.com");
        user.setPassword("password123");
        user.setRole("USER");

        assertEquals(id, user.getId());
        assertEquals("Paul Atreides", user.getName());
        assertEquals("paul.atreides@atreides.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testUserConstructor() {
        ObjectId id = new ObjectId();
        User user = new User(id, "Duncan Idaho", "duncan.idaho@atreides.com", "swordmaster", "ADMIN");

        assertEquals(id, user.getId());
        assertEquals("Duncan Idaho", user.getName());
        assertEquals("duncan.idaho@atreides.com", user.getEmail());
        assertEquals("swordmaster", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testLoginRequestGettersAndSetters() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        assertEquals("test@example.com", loginRequest.getEmail());
        assertEquals("password123", loginRequest.getPassword());
    }

    @Test
    void testLoginResponseConstructor() {
        LoginResponse response = new LoginResponse(true, "ADMIN");

        assertTrue(response.isSuccess());
        assertEquals("ADMIN", response.getUserRole());
    }

    @Test
    void testLoginResponseGettersAndSetters() {
        LoginResponse response = new LoginResponse(false, "");
        response.setSuccess(true);
        response.setUserRole("USER");

        assertTrue(response.isSuccess());
        assertEquals("USER", response.getUserRole());
    }
}