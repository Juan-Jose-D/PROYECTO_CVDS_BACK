package edu.eci.cvds.ReservationProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;
import org.bson.types.ObjectId;

@Document(collection = "usuarios")

/*
 * Clase encargada de modelar una reserva
 */
public class User {

    @Id
    private ObjectId id;

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("password")
    private String password;


    public User(ObjectId id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

