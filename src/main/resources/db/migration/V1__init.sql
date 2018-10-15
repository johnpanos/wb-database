CREATE TABLE wb_user (
    id SERIAL PRIMARY KEY NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE role (
    id SERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE user_with_role (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL
);

CREATE TABLE location (
    id SERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE vendor (
    id SERIAL PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    website TEXT NOT NULL
);

CREATE TABLE part (
    id SERIAL PRIMARY KEY NOT NULL,
    location_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    sublocation TEXT NOT NULL,
    nomenclature TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    min_quantity INTEGER NOT NULL
);

CREATE TABLE part_vendor_information (
    id SERIAL PRIMARY KEY NOT NULL,
    part_number TEXT NOT NULL,
    part_id INTEGER NOT NULL,
    vendor_id INTEGER NOT NULL,
    vendor_information_id INTEGER NOT NULL
);