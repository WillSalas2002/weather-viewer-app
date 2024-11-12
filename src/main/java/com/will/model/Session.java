package com.will.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "_session")
public class Session {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @OneToOne
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
