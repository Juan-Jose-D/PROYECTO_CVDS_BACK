package edu.eci.cvds.ReservationProject;

// Importacion de paquetes
import edu.eci.cvds.ReservationProject.model.*;
import edu.eci.cvds.ReservationProject.repository.*;
import edu.eci.cvds.ReservationProject.service.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReservationProjectApplicationTests {

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
}
