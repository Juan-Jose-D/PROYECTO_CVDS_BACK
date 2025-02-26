package edu.eci.cvds.ReservationProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;
import org.bson.types.ObjectId;

@Document(collection = "reservas")

public class Reservation {

    @Id
    private ObjectId id;

    @Field("user")
    private String user;

    @Field("date")
    private Date date;

    @Field("initialTime")
    private String initialTime;

    @Field("finalTime")
    private String finalTime;

    @Field("status")
    private Boolean status;

    public Reservation(ObjectId  id, String user, Date date, String initialTime, String finalTime, Boolean status) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
        this.status = status;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

