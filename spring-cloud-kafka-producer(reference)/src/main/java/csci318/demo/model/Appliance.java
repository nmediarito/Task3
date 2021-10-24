package csci318.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Appliance {

    private int id;
    private String uid;
    private String brand;
    private String equipment;

    public Appliance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Appliance{" +
                "id='" + id + '\'' +
                ", uid=" + uid +
                ", brand=" + brand +
                ", equipment=" + equipment +
                '}';
    }
}
