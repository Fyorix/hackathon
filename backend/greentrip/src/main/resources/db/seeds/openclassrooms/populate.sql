-- Seed data for OpenClassrooms
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (16, 'OpenClassrooms', '493861273', 80, 350.97, 21876, 2194.6, 'openclassrooms.png', NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    siren_number = EXCLUDED.siren_number,
    total_employees = EXCLUDED.total_employees,
    total_co2_saved = EXCLUDED.total_co2_saved,
    total_points = EXCLUDED.total_points,
    total_km = EXCLUDED.total_km,
    logo_path = EXCLUDED.logo_path;

-- Users for OpenClassrooms
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (318, 'Laura Dupont', 'laura.dupont318@openclassrooms.com', 'password123', 'ADMIN', 4471, 64.55, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (319, 'Alexandre Roussel', 'alexandre.roussel319@openclassrooms.com', 'password123', 'USER', 4097, 58.17, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (320, 'Pierre Dubois', 'pierre.dubois320@openclassrooms.com', 'password123', 'USER', 2454, 57.27, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (321, 'Camille Petit', 'camille.petit321@openclassrooms.com', 'password123', 'USER', 923, 17.08, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (322, 'Charles Martin', 'charles.martin322@openclassrooms.com', 'password123', 'USER', 476, 5.56, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (323, 'Sarah Michel', 'sarah.michel323@openclassrooms.com', 'password123', 'USER', 3628, 53.91, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (324, 'Paul Dupont', 'paul.dupont324@openclassrooms.com', 'password123', 'USER', 1575, 17.48, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (325, 'Laura Lefebvre', 'laura.lefebvre325@openclassrooms.com', 'password123', 'USER', 2072, 36.91, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (326, 'Lea Roux', 'lea.roux326@openclassrooms.com', 'password123', 'USER', 2180, 40.04, 16, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

