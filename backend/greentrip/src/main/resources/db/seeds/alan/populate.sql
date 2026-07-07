-- Seed data for Alan
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (3, 'Alan', '818320478', 250, 402.25, 27679, 2611.6, 'alan.png', NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    siren_number = EXCLUDED.siren_number,
    total_employees = EXCLUDED.total_employees,
    total_co2_saved = EXCLUDED.total_co2_saved,
    total_points = EXCLUDED.total_points,
    total_km = EXCLUDED.total_km,
    logo_path = EXCLUDED.logo_path;

-- Users for Alan
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (140, 'Nicolas Roussel', 'nicolas.roussel140@alan.eu', 'password123', 'ADMIN', 4339, 61.61, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (141, 'Pierre Simon', 'pierre.simon141@alan.eu', 'password123', 'USER', 272, 4.03, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (142, 'Lea Martin', 'lea.martin142@alan.eu', 'password123', 'USER', 681, 9.29, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (143, 'Pierre Roussel', 'pierre.roussel143@alan.eu', 'password123', 'USER', 357, 5.19, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (144, 'Lea Moreau', 'lea.moreau144@alan.eu', 'password123', 'USER', 4076, 48.1, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (145, 'Eva Roussel', 'eva.roussel145@alan.eu', 'password123', 'USER', 4777, 71.04, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (146, 'Alice Guerin', 'alice.guerin146@alan.eu', 'password123', 'USER', 3434, 39.02, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (147, 'Camille Michel', 'camille.michel147@alan.eu', 'password123', 'USER', 3569, 71.04, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (148, 'Marie Dupont', 'marie.dupont148@alan.eu', 'password123', 'USER', 906, 14.4, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (149, 'Manon Henry', 'manon.henry149@alan.eu', 'password123', 'USER', 995, 12.44, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (150, 'Nicolas Robert', 'nicolas.robert150@alan.eu', 'password123', 'USER', 3556, 51.64, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (151, 'Laura Nicolas', 'laura.nicolas151@alan.eu', 'password123', 'USER', 717, 14.45, 3, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

