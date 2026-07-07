-- Seed data for Swile
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (7, 'Swile', '824042454', 120, 386.67, 21542, 2241.9, 'swile.png', NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    siren_number = EXCLUDED.siren_number,
    total_employees = EXCLUDED.total_employees,
    total_co2_saved = EXCLUDED.total_co2_saved,
    total_points = EXCLUDED.total_points,
    total_km = EXCLUDED.total_km,
    logo_path = EXCLUDED.logo_path;

-- Users for Swile
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (197, 'Zoe Fontaine', 'zoe.fontaine197@swile.co', 'password123', 'ADMIN', 1714, 20.59, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (198, 'Ines Nicolas', 'ines.nicolas198@swile.co', 'password123', 'USER', 2804, 54.82, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (199, 'Sophie Fontaine', 'sophie.fontaine199@swile.co', 'password123', 'USER', 2560, 47.92, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (200, 'Antoine Lefebvre', 'antoine.lefebvre200@swile.co', 'password123', 'USER', 2522, 35.87, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (201, 'Ines Lefebvre', 'ines.lefebvre201@swile.co', 'password123', 'USER', 1525, 23.94, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (202, 'Chloe Muller', 'chloe.muller202@swile.co', 'password123', 'USER', 103, 1.34, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (203, 'Manon Fournier', 'manon.fournier203@swile.co', 'password123', 'USER', 2739, 44.35, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (204, 'Lucas Bertrand', 'lucas.bertrand204@swile.co', 'password123', 'USER', 3976, 95.96, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (205, 'Zoe Richard', 'zoe.richard205@swile.co', 'password123', 'USER', 794, 13.51, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (206, 'Clara Simon', 'clara.simon206@swile.co', 'password123', 'USER', 865, 19.02, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (207, 'Ines Laurent', 'ines.laurent207@swile.co', 'password123', 'USER', 1940, 29.35, 7, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

