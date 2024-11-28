--liquibase formatted sql

-- changeset Ilkham:add-unique-on-lon-lat-columns
ALTER TABLE _location ADD CONSTRAINT unique_lon_lat_values UNIQUE (longitude, latitude);

