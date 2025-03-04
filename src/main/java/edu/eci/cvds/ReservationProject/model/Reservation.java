package edu.eci.cvds.ReservationProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;
import org.bson.types.ObjectId;

@Document(collection = "reservas")

/*
 * Clase encargada de modelar una reserva
 */
public class Reservation {

    @Id
    private ObjectId id;

    @Field("user")
    private User user;

    @Field("date")
    private Date date;

    @Field("initialTime")
    private String initialTime;

    @Field("finalTime")
    private String finalTime;

    @Field("status")
    private Boolean status;
    
    @Field("purpose")
    private String purpose;

    @Field("laboratory")
    private Laboratory laboratory;

    public Reservation(ObjectId  id, User user, Date date, String initialTime, String finalTime, Boolean status, String purpose, Laboratory laboratory) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
        this.status = status;
        this.purpose = purpose;
        this.laboratory = laboratory;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }
}

