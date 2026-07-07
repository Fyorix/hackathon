-- Seed data for Back Market
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (5, 'Back Market', '807923761', 300, 430.42, 26780, 2624.0, 'backmarket.png', NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    siren_number = EXCLUDED.siren_number,
    total_employees = EXCLUDED.total_employees,
    total_co2_saved = EXCLUDED.total_co2_saved,
    total_points = EXCLUDED.total_points,
    total_km = EXCLUDED.total_km,
    logo_path = EXCLUDED.logo_path;

-- Users for Back Market
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (167, 'Sophie Thomas', 'sophie.thomas167@backmarket.com', 'password123', 'ADMIN', 4504, 58.42, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (168, 'Eva Michel', 'eva.michel168@backmarket.com', 'password123', 'USER', 663, 11.99, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (169, 'Julie Roux', 'julie.roux169@backmarket.com', 'password123', 'USER', 4550, 89.13, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (170, 'Manon Bonnet', 'manon.bonnet170@backmarket.com', 'password123', 'USER', 4433, 71.81, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (171, 'Emma Nicolas', 'emma.nicolas171@backmarket.com', 'password123', 'USER', 948, 14.86, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (172, 'Sophie Roussel', 'sophie.roussel172@backmarket.com', 'password123', 'USER', 976, 14.52, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (173, 'Emma Girard', 'emma.girard173@backmarket.com', 'password123', 'USER', 1825, 27.85, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (174, 'Maxime Henry', 'maxime.henry174@backmarket.com', 'password123', 'USER', 2262, 32.9, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (175, 'Eva Henry', 'eva.henry175@backmarket.com', 'password123', 'USER', 516, 7.01, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (176, 'Mathieu Bernard', 'mathieu.bernard176@backmarket.com', 'password123', 'USER', 129, 1.6, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (177, 'Mathieu Richard', 'mathieu.richard177@backmarket.com', 'password123', 'USER', 3719, 61.79, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (178, 'Jean Petit', 'jean.petit178@backmarket.com', 'password123', 'USER', 716, 15.94, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (179, 'Thomas Morel', 'thomas.morel179@backmarket.com', 'password123', 'USER', 395, 7.99, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (180, 'Thomas Leroy', 'thomas.leroy180@backmarket.com', 'password123', 'USER', 1144, 14.61, 5, NOW())
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    email = EXCLUDED.email,
    carbon_points_balance = EXCLUDED.carbon_points_balance,
    total_co2_saved = EXCLUDED.total_co2_saved;

