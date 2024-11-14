--liquibase formatted sql

-- changeset Ilkham:create-table-user

CREATE TABLE _user
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- changeset Ilkham:create-table-location
CREATE TABLE _location
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title     VARCHAR(255)  NOT NULL,
    latitude  DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

-- changeset Ilkham:create-table-session
CREATE TABLE _session
(
    uuid       UUID PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user (id) ON DELETE CASCADE
);

-- changeset Ilkham:create-table-user_location
CREATE TABLE _user_location
(
    user_id     BIGINT,
    location_id BIGINT,
    PRIMARY KEY (user_id, location_id),
    FOREIGN KEY (user_id) REFERENCES _user (id) ON DELETE SET NULL,
    FOREIGN KEY (location_id) REFERENCES _location (id) ON DELETE SET NULL
)