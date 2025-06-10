CREATE DATABASE vehicle_management;

USE vehicle_management;

CREATE TABLE vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(20) UNIQUE NOT NULL,
    model VARCHAR(50) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO vehicles (registration_number, model, owner_name, vehicle_type) 
VALUES 
    ('KA01AB1234', 'Honda City', 'Rahul Sharma', 'Sedan'),
    ('MH02XY5678', 'Toyota Innova', 'Anjali Patel', 'SUV'),
    ('DL03PQ7890', 'Maruti Swift', 'Vikram Singh', 'Hatchback');
SELECT * FROM vehicles;
UPDATE vehicles 
SET owner_name = 'Ravi Kumar' 
WHERE registration_number = 'KA01AB1234';
DELETE FROM vehicles WHERE registration_number = 'DL03PQ7890';
SELECT * FROM vehicles WHERE registration_number = 'MH02XY5678';
