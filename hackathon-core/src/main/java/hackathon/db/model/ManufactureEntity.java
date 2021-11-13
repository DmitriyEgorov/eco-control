package hackathon.db.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MANUFACTURE")
public class ManufactureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INN")
    private String inn;

    @Column(name = "ACTIVITY")
    private String activity;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NORTH")
    private BigDecimal north;

    @Column(name = "WEST")
    private BigDecimal west;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getNorth() {
        return north;
    }

    public void setNorth(BigDecimal north) {
        this.north = north;
    }

    public BigDecimal getWest() {
        return west;
    }

    public void setWest(BigDecimal west) {
        this.west = west;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManufactureEntity that = (ManufactureEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(inn, that.inn)
                .append(activity, that.activity)
                .append(address, that.address)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(inn)
                .append(activity)
                .append(address)
                .toHashCode();
    }

}
