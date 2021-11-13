package hackathon.model;

import java.math.BigDecimal;

public class Manufacture {

    private String id;

    private String city;

    private String type;

    private String address;

    private String inn;

    private String coords;

    private Boolean licence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public Boolean getLicence() {
        return licence;
    }

    public void setLicence(Boolean licence) {
        this.licence = licence;
    }
}
