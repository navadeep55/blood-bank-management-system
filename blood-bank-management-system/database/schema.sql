CREATE DATABASE IF NOT EXISTS blood_bank_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blood_bank_db;

-- Spring Data JPA creates/updates these tables in local development.
-- This reference schema is provided to help understand the main entities.
CREATE TABLE IF NOT EXISTS donors (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(150),
  blood_group VARCHAR(3) NOT NULL,
  city VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS blood_inventory (
  blood_group VARCHAR(3) PRIMARY KEY,
  units INT NOT NULL DEFAULT 0 CHECK (units >= 0)
);

CREATE TABLE IF NOT EXISTS blood_requests (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hospital_name VARCHAR(150) NOT NULL,
  contact_name VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  blood_group VARCHAR(3) NOT NULL,
  units INT NOT NULL CHECK (units >= 1),
  notes VARCHAR(500),
  status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
  created_at DATETIME NOT NULL
);
