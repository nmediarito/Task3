package csci318.demo.model;

public class BrandQuantity {
    private String name;
    private long quantity;

    public BrandQuantity() {
    }

    public BrandQuantity(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BrandQuantity{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
