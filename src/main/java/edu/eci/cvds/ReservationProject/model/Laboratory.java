package edu.eci.cvds.ReservationProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.bson.types.ObjectId;

@Document(collection = "laboratorios")

/**
 * Clase encargada de modelar un laboratorio
 */
public class Laboratory {

    @Id
    private

    id;

    @Field("name")
    private String name;

    @Field("capacity")
    private int capacity;

    @Field("location")
    private String location;

    @Field("description")
    private String description;

    @Field("available")
    private Boolean available;

    @Field("equipmentDetails")
    private String equipmentDetails;

    public Laboratory(ObjectId id, String name, int capacity, String location, String description, Boolean available,
                      String equipmentDetails) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.description = description;
        this.available = available;
        this.equipmentDetails = equipmentDetails;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getEquipmentDetails() {
        return equipmentDetails;
    }

    public void setEquipmentDetails(String equipmentDetails) {
        this.equipmentDetails = equipmentDetails;
    }
}
