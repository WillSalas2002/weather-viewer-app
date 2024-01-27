package com.will.weather.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sessions")
public class UserSession {
    @Id
    @Column(name = "id")
    private String id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Override
    public String toString() {
        return "Session[" +
                "id=" + id +
                ", user=" + user +
                ", expiresAt=" + expiresAt +
                ']';
    }
}
