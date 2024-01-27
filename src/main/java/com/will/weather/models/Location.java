package com.will.weather.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "latitude", columnDefinition = "DECIMAL(10,6)")
    private BigDecimal latitude;
    @Column(name = "longitude", columnDefinition = "DECIMAL(10,6)")
    private BigDecimal longitude;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_locations",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Location(String name, BigDecimal longitude, BigDecimal latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "Location[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ']';
    }
}
