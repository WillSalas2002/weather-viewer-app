package com.will.weather.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "users", fetch = FetchType.EAGER)
    private List<Location> locations;
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private UserSession userSession;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
