-- Seed data for BlaBlaCar
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (6, 'BlaBlaCar', '491902095', 400, 565.39, 32800, 3339.7, 'blablacar.png', NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    siren_number = EXCLUDED.siren_number,
    total_employees = EXCLUDED.total_employees,
    total_co2_saved = EXCLUDED.total_co2_saved,
    total_points = EXCLUDED.total_points,
    total_km = EXCLUDED.total_km,
    logo_path = EXCLUDED.logo_path;

-- Users for BlaBlaCar
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (181, 'Eva Guerin', 'eva.guerin181@blablacar.com', 'password123', 'ADMIN', 426, 6.97, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (182, 'Lea Dupont', 'lea.dupont182@blablacar.com', 'password123', 'USER', 942, 15.61, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (183, 'Laura Leroy', 'laura.leroy183@blablacar.com', 'password123', 'USER', 1366, 22.97, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (184, 'Julie Guerin', 'julie.guerin184@blablacar.com', 'password123', 'USER', 1550, 21.87, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (185, 'Zoe Nicolas', 'zoe.nicolas185@blablacar.com', 'password123', 'USER', 2821, 50.61, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (186, 'Ines Henry', 'ines.henry186@blablacar.com', 'password123', 'USER', 2132, 38.42, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (187, 'Sophie Lefebvre', 'sophie.lefebvre187@blablacar.com', 'password123', 'USER', 417, 6.79, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (188, 'Florian Nicolas', 'florian.nicolas188@blablacar.com', 'password123', 'USER', 3870, 73.49, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (189, 'Laura Dubois', 'laura.dubois189@blablacar.com', 'password123', 'USER', 1926, 21.72, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (190, 'Antoine Moreau', 'antoine.moreau190@blablacar.com', 'password123', 'USER', 668, 11.73, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (191, 'Maxime Bertrand', 'maxime.bertrand191@blablacar.com', 'password123', 'USER', 3374, 73.9, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (192, 'Antoine Martin', 'antoine.martin192@blablacar.com', 'password123', 'USER', 1044, 17.55, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (193, 'Paul Moreau', 'paul.moreau193@blablacar.com', 'password123', 'USER', 413, 5.69, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (194, 'Zoe Guerin', 'zoe.guerin194@blablacar.com', 'password123', 'USER', 2669, 56.81, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (195, 'Sophie Lefebvre', 'sophie.lefebvre195@blablacar.com', 'password123', 'USER', 4823, 52.58, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (196, 'Camille Martin', 'camille.martin196@blablacar.com', 'password123', 'USER', 4359, 88.68, 6, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

