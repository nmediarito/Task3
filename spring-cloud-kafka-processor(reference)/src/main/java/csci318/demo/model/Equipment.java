package csci318.demo.model;

public class Equipment {

    private String brand;
    private String equipment;

    public Equipment() {}

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
        return "Equipment{" +
                "brand='" + brand + '\'' +
                ", equipment='" + equipment + '\'' +
                '}';
    }
}
