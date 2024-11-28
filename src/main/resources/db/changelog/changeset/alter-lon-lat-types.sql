--liquibase formatted sql

-- changeset Ilkham:alter-table-location
ALTER TABLE _location
    ALTER COLUMN latitude TYPE DECIMAL(10, 7),
    ALTER COLUMN longitude TYPE DECIMAL(10, 7);