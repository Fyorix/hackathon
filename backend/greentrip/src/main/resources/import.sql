-- ========================================================
-- SEED DATA FOR GREEN TRIP APPLICATION
-- ========================================================

-- Clean existing data in dependency order
DELETE FROM TRIPS;
DELETE FROM USERS;
DELETE FROM COMPANIES;

-- ========================================================
-- COMPANY: Takima (SIREN: 479155989)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (1, 'Takima', '479155989', 50, 1129.39, 64779, 6477.9, 'takima.png', NOW());

-- Users belonging to Takima
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (10, 'Alex', 'alex@takima.fr', 'password123', 'USER', 12000, 240.0, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (11, 'Sarah', 'sarah@takima.fr', 'password123', 'USER', 8000, 160.0, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (12, 'Antoine', 'antoine@takima.fr', 'password123', 'ADMIN', 4000, 80.0, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (103, 'Lea Bertrand', 'lea.bertrand103@takima.fr', 'password123', 'USER', 317, 6.23, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (104, 'Alexandre Morel', 'alexandre.morel104@takima.fr', 'password123', 'USER', 3536, 56.2, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (105, 'Manon Henry', 'manon.henry105@takima.fr', 'password123', 'USER', 153, 2.3, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (106, 'Camille Simon', 'camille.simon106@takima.fr', 'password123', 'USER', 2376, 44.2, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (107, 'Antoine Petit', 'antoine.petit107@takima.fr', 'password123', 'USER', 859, 12.75, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (108, 'Sarah Girard', 'sarah.girard108@takima.fr', 'password123', 'USER', 2266, 49.09, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (109, 'Chloe Petit', 'chloe.petit109@takima.fr', 'password123', 'USER', 3200, 39.73, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (110, 'Maxime Girard', 'maxime.girard110@takima.fr', 'password123', 'USER', 3062, 60.13, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (111, 'Marie Dupont', 'marie.dupont111@takima.fr', 'password123', 'USER', 1966, 47.66, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (112, 'Laura Dubois', 'laura.dubois112@takima.fr', 'password123', 'USER', 927, 14.6, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (113, 'Florian Michel', 'florian.michel113@takima.fr', 'password123', 'USER', 1432, 19.14, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (114, 'Mathieu Lambert', 'mathieu.lambert114@takima.fr', 'password123', 'USER', 684, 9.79, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (115, 'Zoe Dubois', 'zoe.dubois115@takima.fr', 'password123', 'USER', 1438, 20.81, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (116, 'Eva Bonnet', 'eva.bonnet116@takima.fr', 'password123', 'USER', 4662, 63.09, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (117, 'Julien Rousseau', 'julien.rousseau117@takima.fr', 'password123', 'USER', 558, 6.14, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (118, 'Antoine Lefebvre', 'antoine.lefebvre118@takima.fr', 'password123', 'USER', 2293, 40.04, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (119, 'Paul Roussel', 'paul.roussel119@takima.fr', 'password123', 'USER', 2677, 40.25, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (120, 'Charles Nicolas', 'charles.nicolas120@takima.fr', 'password123', 'USER', 3858, 44.3, 1, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (121, 'Zoe Morel', 'zoe.morel121@takima.fr', 'password123', 'USER', 4515, 72.94, 1, NOW());

-- ========================================================
-- COMPANY: Decathlon (SIREN: 306138900)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (2, 'Decathlon', '306138900', 15000, 760.2, 41146, 4283.8, 'decathlon.png', NOW());

-- Users belonging to Decathlon
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (122, 'Charles Fournier', 'charles.fournier122@decathlon.fr', 'password123', 'ADMIN', 3371, 69.98, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (123, 'Thomas Bertrand', 'thomas.bertrand123@decathlon.fr', 'password123', 'USER', 4142, 43.2, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (124, 'Sophie Robert', 'sophie.robert124@decathlon.fr', 'password123', 'USER', 1410, 25.55, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (125, 'Pierre Lefebvre', 'pierre.lefebvre125@decathlon.fr', 'password123', 'USER', 3226, 55.88, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (126, 'Mathieu Morel', 'mathieu.morel126@decathlon.fr', 'password123', 'USER', 194, 2.73, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (127, 'Charles Morel', 'charles.morel127@decathlon.fr', 'password123', 'USER', 2285, 38.97, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (128, 'Emma Leroy', 'emma.leroy128@decathlon.fr', 'password123', 'USER', 1395, 29.49, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (129, 'Charles Fontaine', 'charles.fontaine129@decathlon.fr', 'password123', 'USER', 2257, 52.64, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (130, 'Hugo Nicolas', 'hugo.nicolas130@decathlon.fr', 'password123', 'USER', 971, 16.71, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (131, 'Maxime Bertrand', 'maxime.bertrand131@decathlon.fr', 'password123', 'USER', 1729, 29.22, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (132, 'Chloe Rousseau', 'chloe.rousseau132@decathlon.fr', 'password123', 'USER', 4444, 93.33, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (133, 'Alice Martin', 'alice.martin133@decathlon.fr', 'password123', 'USER', 1016, 24.74, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (134, 'Florian Guerin', 'florian.guerin134@decathlon.fr', 'password123', 'USER', 2619, 33.8, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (135, 'Paul Thomas', 'paul.thomas135@decathlon.fr', 'password123', 'USER', 801, 17.64, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (136, 'Julien Morel', 'julien.morel136@decathlon.fr', 'password123', 'USER', 1130, 16.12, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (137, 'Chloe Richard', 'chloe.richard137@decathlon.fr', 'password123', 'USER', 2271, 41.48, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (138, 'Lucas Nicolas', 'lucas.nicolas138@decathlon.fr', 'password123', 'USER', 4517, 94.08, 2, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (139, 'Alexandre Laurent', 'alexandre.laurent139@decathlon.fr', 'password123', 'USER', 3368, 74.64, 2, NOW());

-- ========================================================
-- COMPANY: Alan (SIREN: 818320478)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (3, 'Alan', '818320478', 250, 402.25, 27679, 2611.6, 'alan.png', NOW());

-- Users belonging to Alan
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (140, 'Nicolas Roussel', 'nicolas.roussel140@alan.eu', 'password123', 'ADMIN', 4339, 61.61, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (141, 'Pierre Simon', 'pierre.simon141@alan.eu', 'password123', 'USER', 272, 4.03, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (142, 'Lea Martin', 'lea.martin142@alan.eu', 'password123', 'USER', 681, 9.29, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (143, 'Pierre Roussel', 'pierre.roussel143@alan.eu', 'password123', 'USER', 357, 5.19, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (144, 'Lea Moreau', 'lea.moreau144@alan.eu', 'password123', 'USER', 4076, 48.1, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (145, 'Eva Roussel', 'eva.roussel145@alan.eu', 'password123', 'USER', 4777, 71.04, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (146, 'Alice Guerin', 'alice.guerin146@alan.eu', 'password123', 'USER', 3434, 39.02, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (147, 'Camille Michel', 'camille.michel147@alan.eu', 'password123', 'USER', 3569, 71.04, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (148, 'Marie Dupont', 'marie.dupont148@alan.eu', 'password123', 'USER', 906, 14.4, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (149, 'Manon Henry', 'manon.henry149@alan.eu', 'password123', 'USER', 995, 12.44, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (150, 'Nicolas Robert', 'nicolas.robert150@alan.eu', 'password123', 'USER', 3556, 51.64, 3, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (151, 'Laura Nicolas', 'laura.nicolas151@alan.eu', 'password123', 'USER', 717, 14.45, 3, NOW());

-- ========================================================
-- COMPANY: Doctolib (SIREN: 794586944)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (4, 'Doctolib', '794586944', 800, 717.9, 41996, 4026.3, 'doctolib.png', NOW());

-- Users belonging to Doctolib
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (152, 'Chloe Petit', 'chloe.petit152@doctolib.fr', 'password123', 'ADMIN', 514, 9.49, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (153, 'Jean Thomas', 'jean.thomas153@doctolib.fr', 'password123', 'USER', 2036, 29.73, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (154, 'Lucas Henry', 'lucas.henry154@doctolib.fr', 'password123', 'USER', 3385, 53.63, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (155, 'Jean Lefebvre', 'jean.lefebvre155@doctolib.fr', 'password123', 'USER', 2272, 52.8, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (156, 'Emma Leroy', 'emma.leroy156@doctolib.fr', 'password123', 'USER', 4652, 83.54, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (157, 'Lucas Laurent', 'lucas.laurent157@doctolib.fr', 'password123', 'USER', 1883, 39.78, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (158, 'Chloe Bernard', 'chloe.bernard158@doctolib.fr', 'password123', 'USER', 2669, 39.18, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (159, 'Hugo Nicolas', 'hugo.nicolas159@doctolib.fr', 'password123', 'USER', 4450, 82.97, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (160, 'Pierre Henry', 'pierre.henry160@doctolib.fr', 'password123', 'USER', 1622, 17.02, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (161, 'Laura Dubois', 'laura.dubois161@doctolib.fr', 'password123', 'USER', 3407, 60.39, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (162, 'Lea Fournier', 'lea.fournier162@doctolib.fr', 'password123', 'USER', 4970, 51.98, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (163, 'Ines Fournier', 'ines.fournier163@doctolib.fr', 'password123', 'USER', 4730, 101.89, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (164, 'Lucas Dupont', 'lucas.dupont164@doctolib.fr', 'password123', 'USER', 2673, 38.2, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (165, 'Ines Bonnet', 'ines.bonnet165@doctolib.fr', 'password123', 'USER', 2557, 53.5, 4, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (166, 'Eva Thomas', 'eva.thomas166@doctolib.fr', 'password123', 'USER', 176, 3.8, 4, NOW());

-- ========================================================
-- COMPANY: Back Market (SIREN: 807923761)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (5, 'Back Market', '807923761', 300, 430.42, 26780, 2624.0, 'backmarket.png', NOW());

-- Users belonging to Back Market
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (167, 'Sophie Thomas', 'sophie.thomas167@backmarket.com', 'password123', 'ADMIN', 4504, 58.42, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (168, 'Eva Michel', 'eva.michel168@backmarket.com', 'password123', 'USER', 663, 11.99, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (169, 'Julie Roux', 'julie.roux169@backmarket.com', 'password123', 'USER', 4550, 89.13, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (170, 'Manon Bonnet', 'manon.bonnet170@backmarket.com', 'password123', 'USER', 4433, 71.81, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (171, 'Emma Nicolas', 'emma.nicolas171@backmarket.com', 'password123', 'USER', 948, 14.86, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (172, 'Sophie Roussel', 'sophie.roussel172@backmarket.com', 'password123', 'USER', 976, 14.52, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (173, 'Emma Girard', 'emma.girard173@backmarket.com', 'password123', 'USER', 1825, 27.85, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (174, 'Maxime Henry', 'maxime.henry174@backmarket.com', 'password123', 'USER', 2262, 32.9, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (175, 'Eva Henry', 'eva.henry175@backmarket.com', 'password123', 'USER', 516, 7.01, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (176, 'Mathieu Bernard', 'mathieu.bernard176@backmarket.com', 'password123', 'USER', 129, 1.6, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (177, 'Mathieu Richard', 'mathieu.richard177@backmarket.com', 'password123', 'USER', 3719, 61.79, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (178, 'Jean Petit', 'jean.petit178@backmarket.com', 'password123', 'USER', 716, 15.94, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (179, 'Thomas Morel', 'thomas.morel179@backmarket.com', 'password123', 'USER', 395, 7.99, 5, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (180, 'Thomas Leroy', 'thomas.leroy180@backmarket.com', 'password123', 'USER', 1144, 14.61, 5, NOW());

-- ========================================================
-- COMPANY: BlaBlaCar (SIREN: 491902095)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (6, 'BlaBlaCar', '491902095', 400, 565.39, 32800, 3339.7, 'blablacar.png', NOW());

-- Users belonging to BlaBlaCar
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (181, 'Eva Guerin', 'eva.guerin181@blablacar.com', 'password123', 'ADMIN', 426, 6.97, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (182, 'Lea Dupont', 'lea.dupont182@blablacar.com', 'password123', 'USER', 942, 15.61, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (183, 'Laura Leroy', 'laura.leroy183@blablacar.com', 'password123', 'USER', 1366, 22.97, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (184, 'Julie Guerin', 'julie.guerin184@blablacar.com', 'password123', 'USER', 1550, 21.87, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (185, 'Zoe Nicolas', 'zoe.nicolas185@blablacar.com', 'password123', 'USER', 2821, 50.61, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (186, 'Ines Henry', 'ines.henry186@blablacar.com', 'password123', 'USER', 2132, 38.42, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (187, 'Sophie Lefebvre', 'sophie.lefebvre187@blablacar.com', 'password123', 'USER', 417, 6.79, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (188, 'Florian Nicolas', 'florian.nicolas188@blablacar.com', 'password123', 'USER', 3870, 73.49, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (189, 'Laura Dubois', 'laura.dubois189@blablacar.com', 'password123', 'USER', 1926, 21.72, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (190, 'Antoine Moreau', 'antoine.moreau190@blablacar.com', 'password123', 'USER', 668, 11.73, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (191, 'Maxime Bertrand', 'maxime.bertrand191@blablacar.com', 'password123', 'USER', 3374, 73.9, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (192, 'Antoine Martin', 'antoine.martin192@blablacar.com', 'password123', 'USER', 1044, 17.55, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (193, 'Paul Moreau', 'paul.moreau193@blablacar.com', 'password123', 'USER', 413, 5.69, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (194, 'Zoe Guerin', 'zoe.guerin194@blablacar.com', 'password123', 'USER', 2669, 56.81, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (195, 'Sophie Lefebvre', 'sophie.lefebvre195@blablacar.com', 'password123', 'USER', 4823, 52.58, 6, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (196, 'Camille Martin', 'camille.martin196@blablacar.com', 'password123', 'USER', 4359, 88.68, 6, NOW());

-- ========================================================
-- COMPANY: Swile (SIREN: 824042454)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (7, 'Swile', '824042454', 120, 386.67, 21542, 2241.9, 'swile.png', NOW());

-- Users belonging to Swile
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (197, 'Zoe Fontaine', 'zoe.fontaine197@swile.co', 'password123', 'ADMIN', 1714, 20.59, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (198, 'Ines Nicolas', 'ines.nicolas198@swile.co', 'password123', 'USER', 2804, 54.82, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (199, 'Sophie Fontaine', 'sophie.fontaine199@swile.co', 'password123', 'USER', 2560, 47.92, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (200, 'Antoine Lefebvre', 'antoine.lefebvre200@swile.co', 'password123', 'USER', 2522, 35.87, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (201, 'Ines Lefebvre', 'ines.lefebvre201@swile.co', 'password123', 'USER', 1525, 23.94, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (202, 'Chloe Muller', 'chloe.muller202@swile.co', 'password123', 'USER', 103, 1.34, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (203, 'Manon Fournier', 'manon.fournier203@swile.co', 'password123', 'USER', 2739, 44.35, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (204, 'Lucas Bertrand', 'lucas.bertrand204@swile.co', 'password123', 'USER', 3976, 95.96, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (205, 'Zoe Richard', 'zoe.richard205@swile.co', 'password123', 'USER', 794, 13.51, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (206, 'Clara Simon', 'clara.simon206@swile.co', 'password123', 'USER', 865, 19.02, 7, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (207, 'Ines Laurent', 'ines.laurent207@swile.co', 'password123', 'USER', 1940, 29.35, 7, NOW());

-- ========================================================
-- COMPANY: PayFit (SIREN: 810697203)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (8, 'PayFit', '810697203', 150, 666.66, 37025, 3666.3, 'payfit.png', NOW());

-- Users belonging to PayFit
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (208, 'Marie Dubois', 'marie.dubois208@payfit.com', 'password123', 'ADMIN', 3992, 82.09, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (209, 'Nicolas Leroy', 'nicolas.leroy209@payfit.com', 'password123', 'USER', 4815, 80.14, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (210, 'Alice Lefebvre', 'alice.lefebvre210@payfit.com', 'password123', 'USER', 2098, 34.01, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (211, 'Charles Rousseau', 'charles.rousseau211@payfit.com', 'password123', 'USER', 973, 15.34, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (212, 'Manon Lambert', 'manon.lambert212@payfit.com', 'password123', 'USER', 4343, 75.23, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (213, 'Eva Henry', 'eva.henry213@payfit.com', 'password123', 'USER', 1094, 21.52, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (214, 'Ines Bertrand', 'ines.bertrand214@payfit.com', 'password123', 'USER', 4678, 104.43, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (215, 'Charles Roux', 'charles.roux215@payfit.com', 'password123', 'USER', 4235, 86.61, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (216, 'Nicolas Roussel', 'nicolas.roussel216@payfit.com', 'password123', 'USER', 1403, 25.79, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (217, 'Mathieu Rousseau', 'mathieu.rousseau217@payfit.com', 'password123', 'USER', 2125, 35.66, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (218, 'Julien Bertrand', 'julien.bertrand218@payfit.com', 'password123', 'USER', 4069, 63.04, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (219, 'Pierre Lambert', 'pierre.lambert219@payfit.com', 'password123', 'USER', 2440, 33.5, 8, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (220, 'Charles Morel', 'charles.morel220@payfit.com', 'password123', 'USER', 760, 9.3, 8, NOW());

-- ========================================================
-- COMPANY: Yuka (SIREN: 817751913)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (9, 'Yuka', '817751913', 11, 501.1, 28672, 2963.7, 'yuka.png', NOW());

-- Users belonging to Yuka
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (221, 'Alexandre Robert', 'alexandre.robert221@yuka.io', 'password123', 'ADMIN', 1852, 24.58, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (222, 'Chloe Roux', 'chloe.roux222@yuka.io', 'password123', 'USER', 3506, 58.79, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (223, 'Guillaume Roussel', 'guillaume.roussel223@yuka.io', 'password123', 'USER', 4884, 70.18, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (224, 'Charles Rousseau', 'charles.rousseau224@yuka.io', 'password123', 'USER', 4816, 55.3, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (225, 'Sarah Laurent', 'sarah.laurent225@yuka.io', 'password123', 'USER', 3294, 80.97, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (226, 'Camille Morel', 'camille.morel226@yuka.io', 'password123', 'USER', 4573, 107.45, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (227, 'Alice Dubois', 'alice.dubois227@yuka.io', 'password123', 'USER', 2335, 27.96, 9, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (228, 'Antoine Dupont', 'antoine.dupont228@yuka.io', 'password123', 'USER', 3412, 75.87, 9, NOW());

-- ========================================================
-- COMPANY: LVMH (SIREN: 338220627)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (10, 'LVMH', '338220627', 50000, 271.21, 17895, 1544.0, 'lvmh.png', NOW());

-- Users belonging to LVMH
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (229, 'Eva Robert', 'eva.robert229@lvmh.fr', 'password123', 'ADMIN', 4475, 57.83, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (230, 'Paul Dupont', 'paul.dupont230@lvmh.fr', 'password123', 'USER', 322, 4.36, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (231, 'Laura Roux', 'laura.roux231@lvmh.fr', 'password123', 'USER', 1588, 20.56, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (232, 'Lucas Roux', 'lucas.roux232@lvmh.fr', 'password123', 'USER', 2777, 53.99, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (233, 'Mathieu Rousseau', 'mathieu.rousseau233@lvmh.fr', 'password123', 'USER', 3553, 41.03, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (234, 'Jean Fontaine', 'jean.fontaine234@lvmh.fr', 'password123', 'USER', 4518, 79.81, 10, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (235, 'Lea Bonnet', 'lea.bonnet235@lvmh.fr', 'password123', 'USER', 662, 13.63, 10, NOW());

-- ========================================================
-- COMPANY: SNCF (SIREN: 552049447)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (11, 'SNCF', '552049447', 60000, 1200.13, 75128, 7233.5, 'sncf.png', NOW());

-- Users belonging to SNCF
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (236, 'Julien Martin', 'julien.martin236@sncf.fr', 'password123', 'ADMIN', 2125, 22.81, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (237, 'Thomas Dubois', 'thomas.dubois237@sncf.fr', 'password123', 'USER', 1134, 14.75, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (238, 'Lucas Roux', 'lucas.roux238@sncf.fr', 'password123', 'USER', 2199, 33.29, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (239, 'Clara Fontaine', 'clara.fontaine239@sncf.fr', 'password123', 'USER', 1038, 15.72, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (240, 'Emma Petit', 'emma.petit240@sncf.fr', 'password123', 'USER', 4840, 59.3, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (241, 'Ines Nicolas', 'ines.nicolas241@sncf.fr', 'password123', 'USER', 3174, 58.27, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (242, 'Pierre Fournier', 'pierre.fournier242@sncf.fr', 'password123', 'USER', 2089, 34.64, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (243, 'Laura Dupont', 'laura.dupont243@sncf.fr', 'password123', 'USER', 1091, 21.54, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (244, 'Marie Michel', 'marie.michel244@sncf.fr', 'password123', 'USER', 4464, 68.1, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (245, 'Hugo Bonnet', 'hugo.bonnet245@sncf.fr', 'password123', 'USER', 2895, 37.76, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (246, 'Alice Petit', 'alice.petit246@sncf.fr', 'password123', 'USER', 3651, 79.37, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (247, 'Florian Roux', 'florian.roux247@sncf.fr', 'password123', 'USER', 1353, 25.49, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (248, 'Maxime Moreau', 'maxime.moreau248@sncf.fr', 'password123', 'USER', 4508, 83.29, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (249, 'Florian Fontaine', 'florian.fontaine249@sncf.fr', 'password123', 'USER', 4953, 92.2, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (250, 'Florian Nicolas', 'florian.nicolas250@sncf.fr', 'password123', 'USER', 809, 12.17, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (251, 'Julien Roux', 'julien.roux251@sncf.fr', 'password123', 'USER', 4768, 78.6, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (252, 'Jean David', 'jean.david252@sncf.fr', 'password123', 'USER', 2762, 34.03, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (253, 'Manon Moreau', 'manon.moreau253@sncf.fr', 'password123', 'USER', 2888, 47.29, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (254, 'Charles Moreau', 'charles.moreau254@sncf.fr', 'password123', 'USER', 4653, 80.36, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (255, 'Pierre Dubois', 'pierre.dubois255@sncf.fr', 'password123', 'USER', 3429, 66.83, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (256, 'Alexandre David', 'alexandre.david256@sncf.fr', 'password123', 'USER', 4120, 49.12, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (257, 'Emma Dubois', 'emma.dubois257@sncf.fr', 'password123', 'USER', 3412, 55.34, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (258, 'Paul Michel', 'paul.michel258@sncf.fr', 'password123', 'USER', 3976, 62.69, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (259, 'Zoe Morel', 'zoe.morel259@sncf.fr', 'password123', 'USER', 2809, 43.69, 11, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (260, 'Emma Moreau', 'emma.moreau260@sncf.fr', 'password123', 'USER', 1988, 23.48, 11, NOW());

-- ========================================================
-- COMPANY: EDF (SIREN: 552081317)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (12, 'EDF', '552081317', 45000, 782.67, 48460, 4865.3, 'edf.png', NOW());

-- Users belonging to EDF
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (261, 'Sophie Fontaine', 'sophie.fontaine261@edf.fr', 'password123', 'ADMIN', 4489, 100.16, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (262, 'Lucas Durand', 'lucas.durand262@edf.fr', 'password123', 'USER', 4066, 66.25, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (263, 'Julien Bertrand', 'julien.bertrand263@edf.fr', 'password123', 'USER', 4988, 59.24, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (264, 'Lucas Laurent', 'lucas.laurent264@edf.fr', 'password123', 'USER', 1963, 27.85, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (265, 'Alexandre Morel', 'alexandre.morel265@edf.fr', 'password123', 'USER', 1136, 22.46, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (266, 'Chloe Laurent', 'chloe.laurent266@edf.fr', 'password123', 'USER', 1134, 23.37, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (267, 'Sophie Henry', 'sophie.henry267@edf.fr', 'password123', 'USER', 200, 3.44, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (268, 'Nicolas Simon', 'nicolas.simon268@edf.fr', 'password123', 'USER', 1610, 27.74, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (269, 'Laura David', 'laura.david269@edf.fr', 'password123', 'USER', 1034, 18.68, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (270, 'Pierre Fournier', 'pierre.fournier270@edf.fr', 'password123', 'USER', 539, 9.33, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (271, 'Emma Thomas', 'emma.thomas271@edf.fr', 'password123', 'USER', 2133, 35.5, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (272, 'Clara Girard', 'clara.girard272@edf.fr', 'password123', 'USER', 1948, 34.18, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (273, 'Eva Roux', 'eva.roux273@edf.fr', 'password123', 'USER', 2535, 63.51, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (274, 'Emma Fournier', 'emma.fournier274@edf.fr', 'password123', 'USER', 593, 12.01, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (275, 'Julien Durand', 'julien.durand275@edf.fr', 'password123', 'USER', 1828, 21.22, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (276, 'Lea Richard', 'lea.richard276@edf.fr', 'password123', 'USER', 4621, 46.13, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (277, 'Nicolas Lambert', 'nicolas.lambert277@edf.fr', 'password123', 'USER', 4964, 60.45, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (278, 'Emma Lambert', 'emma.lambert278@edf.fr', 'password123', 'USER', 2416, 43.2, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (279, 'Ines Dubois', 'ines.dubois279@edf.fr', 'password123', 'USER', 2267, 46.14, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (280, 'Ines Guerin', 'ines.guerin280@edf.fr', 'password123', 'USER', 1720, 29.1, 12, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (281, 'Maxime Robert', 'maxime.robert281@edf.fr', 'password123', 'USER', 2276, 32.71, 12, NOW());

-- ========================================================
-- COMPANY: Mirakl (SIREN: 532881331)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (13, 'Mirakl', '532881331', 200, 482.75, 27473, 2762.4, 'mirakl.png', NOW());

-- Users belonging to Mirakl
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (282, 'Julie Guerin', 'julie.guerin282@mirakl.com', 'password123', 'ADMIN', 2619, 55.04, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (283, 'Eva Laurent', 'eva.laurent283@mirakl.com', 'password123', 'USER', 3697, 59.32, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (284, 'Alexandre Lefebvre', 'alexandre.lefebvre284@mirakl.com', 'password123', 'USER', 2330, 39.47, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (285, 'Pierre Girard', 'pierre.girard285@mirakl.com', 'password123', 'USER', 426, 9.52, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (286, 'Clara Moreau', 'clara.moreau286@mirakl.com', 'password123', 'USER', 311, 5.62, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (287, 'Florian Henry', 'florian.henry287@mirakl.com', 'password123', 'USER', 4812, 60.79, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (288, 'Julien Dupont', 'julien.dupont288@mirakl.com', 'password123', 'USER', 2307, 46.66, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (289, 'Julie David', 'julie.david289@mirakl.com', 'password123', 'USER', 4351, 97.66, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (290, 'Julie Fournier', 'julie.fournier290@mirakl.com', 'password123', 'USER', 3670, 65.44, 13, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (291, 'Pierre David', 'pierre.david291@mirakl.com', 'password123', 'USER', 2950, 43.23, 13, NOW());

-- ========================================================
-- COMPANY: ContentSquare (SIREN: 512217696)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (14, 'ContentSquare', '512217696', 500, 796.6, 48627, 4836.0, 'contentsquare.png', NOW());

-- Users belonging to ContentSquare
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (292, 'Sophie Henry', 'sophie.henry292@contentsquare.com', 'password123', 'ADMIN', 1417, 25.02, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (293, 'Emma Dupont', 'emma.dupont293@contentsquare.com', 'password123', 'USER', 3380, 66.57, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (294, 'Nicolas Thomas', 'nicolas.thomas294@contentsquare.com', 'password123', 'USER', 2676, 31.73, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (295, 'Julien Lefebvre', 'julien.lefebvre295@contentsquare.com', 'password123', 'USER', 4315, 58.57, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (296, 'Laura Morel', 'laura.morel296@contentsquare.com', 'password123', 'USER', 3884, 52.02, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (297, 'Sarah Girard', 'sarah.girard297@contentsquare.com', 'password123', 'USER', 4183, 86.1, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (298, 'Lucas Moreau', 'lucas.moreau298@contentsquare.com', 'password123', 'USER', 4599, 58.33, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (299, 'Charles Lambert', 'charles.lambert299@contentsquare.com', 'password123', 'USER', 4070, 75.06, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (300, 'Clara Guerin', 'clara.guerin300@contentsquare.com', 'password123', 'USER', 2060, 33.74, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (301, 'Jean Morel', 'jean.morel301@contentsquare.com', 'password123', 'USER', 3442, 63.18, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (302, 'Eva Petit', 'eva.petit302@contentsquare.com', 'password123', 'USER', 3880, 84.51, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (303, 'Thomas David', 'thomas.david303@contentsquare.com', 'password123', 'USER', 2490, 36.82, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (304, 'Florian David', 'florian.david304@contentsquare.com', 'password123', 'USER', 3968, 62.36, 14, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (305, 'Guillaume Durand', 'guillaume.durand305@contentsquare.com', 'password123', 'USER', 4263, 62.59, 14, NOW());

-- ========================================================
-- COMPANY: ManoMano (SIREN: 752943269)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (15, 'ManoMano', '752943269', 450, 648.2, 33794, 3571.1, 'manomano.png', NOW());

-- Users belonging to ManoMano
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (306, 'Pierre Moreau', 'pierre.moreau306@manomano.fr', 'password123', 'ADMIN', 3499, 65.1, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (307, 'Mathieu Muller', 'mathieu.muller307@manomano.fr', 'password123', 'USER', 121, 1.65, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (308, 'Paul Fournier', 'paul.fournier308@manomano.fr', 'password123', 'USER', 4111, 77.59, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (309, 'Alice Michel', 'alice.michel309@manomano.fr', 'password123', 'USER', 2823, 50.25, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (310, 'Nicolas Nicolas', 'nicolas.nicolas310@manomano.fr', 'password123', 'USER', 2736, 68.48, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (311, 'Lea Fournier', 'lea.fournier311@manomano.fr', 'password123', 'USER', 3237, 57.13, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (312, 'Marie Simon', 'marie.simon312@manomano.fr', 'password123', 'USER', 3974, 86.49, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (313, 'Guillaume Dupont', 'guillaume.dupont313@manomano.fr', 'password123', 'USER', 1346, 16.61, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (314, 'Hugo Fournier', 'hugo.fournier314@manomano.fr', 'password123', 'USER', 2819, 67.12, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (315, 'Nicolas Petit', 'nicolas.petit315@manomano.fr', 'password123', 'USER', 4408, 62.37, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (316, 'Thomas Leroy', 'thomas.leroy316@manomano.fr', 'password123', 'USER', 1364, 22.43, 15, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (317, 'Mathieu Simon', 'mathieu.simon317@manomano.fr', 'password123', 'USER', 3356, 72.98, 15, NOW());

-- ========================================================
-- COMPANY: OpenClassrooms (SIREN: 493861273)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (16, 'OpenClassrooms', '493861273', 80, 350.97, 21876, 2194.6, 'openclassrooms.png', NOW());

-- Users belonging to OpenClassrooms
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (318, 'Laura Dupont', 'laura.dupont318@openclassrooms.com', 'password123', 'ADMIN', 4471, 64.55, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (319, 'Alexandre Roussel', 'alexandre.roussel319@openclassrooms.com', 'password123', 'USER', 4097, 58.17, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (320, 'Pierre Dubois', 'pierre.dubois320@openclassrooms.com', 'password123', 'USER', 2454, 57.27, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (321, 'Camille Petit', 'camille.petit321@openclassrooms.com', 'password123', 'USER', 923, 17.08, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (322, 'Charles Martin', 'charles.martin322@openclassrooms.com', 'password123', 'USER', 476, 5.56, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (323, 'Sarah Michel', 'sarah.michel323@openclassrooms.com', 'password123', 'USER', 3628, 53.91, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (324, 'Paul Dupont', 'paul.dupont324@openclassrooms.com', 'password123', 'USER', 1575, 17.48, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (325, 'Laura Lefebvre', 'laura.lefebvre325@openclassrooms.com', 'password123', 'USER', 2072, 36.91, 16, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (326, 'Lea Roux', 'lea.roux326@openclassrooms.com', 'password123', 'USER', 2180, 40.04, 16, NOW());

-- ========================================================
-- COMPANY: Evaneos (SIREN: 513191114)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (17, 'Evaneos', '513191114', 60, 565.32, 29526, 3216.0, 'evaneos.png', NOW());

-- Users belonging to Evaneos
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (327, 'Charles Guerin', 'charles.guerin327@evaneos.com', 'password123', 'ADMIN', 3911, 85.22, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (328, 'Julie Thomas', 'julie.thomas328@evaneos.com', 'password123', 'USER', 3718, 95.82, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (329, 'Emma Bonnet', 'emma.bonnet329@evaneos.com', 'password123', 'USER', 3575, 63.74, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (330, 'Emma Durand', 'emma.durand330@evaneos.com', 'password123', 'USER', 3251, 48.48, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (331, 'Guillaume Fournier', 'guillaume.fournier331@evaneos.com', 'password123', 'USER', 3040, 68.65, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (332, 'Emma Martin', 'emma.martin332@evaneos.com', 'password123', 'USER', 3342, 53.7, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (333, 'Ines Rousseau', 'ines.rousseau333@evaneos.com', 'password123', 'USER', 501, 11.34, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (334, 'Florian Roussel', 'florian.roussel334@evaneos.com', 'password123', 'USER', 2444, 38.81, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (335, 'Manon Michel', 'manon.michel335@evaneos.com', 'password123', 'USER', 1894, 36.38, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (336, 'Ines Rousseau', 'ines.rousseau336@evaneos.com', 'password123', 'USER', 1220, 26.99, 17, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (337, 'Maxime Bernard', 'maxime.bernard337@evaneos.com', 'password123', 'USER', 2630, 36.19, 17, NOW());

-- ========================================================
-- COMPANY: Veepee (SIREN: 508493175)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (18, 'Veepee', '508493175', 1000, 579.4, 34785, 3531.2, 'veepee.png', NOW());

-- Users belonging to Veepee
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (338, 'Sarah Fontaine', 'sarah.fontaine338@veepee.com', 'password123', 'ADMIN', 1176, 14.7, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (339, 'Zoe Leroy', 'zoe.leroy339@veepee.com', 'password123', 'USER', 1538, 26.9, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (340, 'Charles Michel', 'charles.michel340@veepee.com', 'password123', 'USER', 4448, 65.54, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (341, 'Julie Moreau', 'julie.moreau341@veepee.com', 'password123', 'USER', 4047, 71.83, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (342, 'Laura Simon', 'laura.simon342@veepee.com', 'password123', 'USER', 1043, 13.14, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (343, 'Julien Dubois', 'julien.dubois343@veepee.com', 'password123', 'USER', 3355, 79.82, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (344, 'Sarah Thomas', 'sarah.thomas344@veepee.com', 'password123', 'USER', 3332, 46.61, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (345, 'Nicolas Michel', 'nicolas.michel345@veepee.com', 'password123', 'USER', 2247, 46.98, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (346, 'Sarah Petit', 'sarah.petit346@veepee.com', 'password123', 'USER', 2015, 36.25, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (347, 'Chloe Simon', 'chloe.simon347@veepee.com', 'password123', 'USER', 1913, 37.19, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (348, 'Nicolas Nicolas', 'nicolas.nicolas348@veepee.com', 'password123', 'USER', 2575, 35.93, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (349, 'Marie Bernard', 'marie.bernard349@veepee.com', 'password123', 'USER', 2593, 40.89, 18, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (350, 'Lea Roussel', 'lea.roussel350@veepee.com', 'password123', 'USER', 4503, 63.62, 18, NOW());

-- ========================================================
-- COMPANY: Luko (SIREN: 824141678)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (19, 'Luko', '824141678', 15, 346.29, 19368, 1972.1, 'luko.png', NOW());

-- Users belonging to Luko
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (351, 'Ines Fontaine', 'ines.fontaine351@luko.eu', 'password123', 'ADMIN', 4525, 85.05, 19, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (352, 'Thomas Roussel', 'thomas.roussel352@luko.eu', 'password123', 'USER', 3498, 75.55, 19, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (353, 'Clara Leroy', 'clara.leroy353@luko.eu', 'password123', 'USER', 2391, 30.53, 19, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (354, 'Nicolas Roux', 'nicolas.roux354@luko.eu', 'password123', 'USER', 2034, 30.18, 19, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (355, 'Ines Michel', 'ines.michel355@luko.eu', 'password123', 'USER', 4560, 97.65, 19, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (356, 'Marie Lefebvre', 'marie.lefebvre356@luko.eu', 'password123', 'USER', 2360, 27.33, 19, NOW());

-- ========================================================
-- COMPANY: Yousign (SIREN: 798242486)
-- ========================================================
INSERT INTO COMPANIES (id, name, siren_number, total_employees, total_co2_saved, total_points, total_km, logo_path, created_at)
VALUES (20, 'Yousign', '798242486', 140, 397.7, 24508, 2328.6, 'yousign.png', NOW());

-- Users belonging to Yousign
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (357, 'Laura Muller', 'laura.muller357@yousign.com', 'password123', 'ADMIN', 3825, 45.19, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (358, 'Maxime Girard', 'maxime.girard358@yousign.com', 'password123', 'USER', 274, 3.45, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (359, 'Thomas Guerin', 'thomas.guerin359@yousign.com', 'password123', 'USER', 4725, 84.6, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (360, 'Chloe Durand', 'chloe.durand360@yousign.com', 'password123', 'USER', 4903, 89.95, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (361, 'Antoine Rousseau', 'antoine.rousseau361@yousign.com', 'password123', 'USER', 1308, 26.2, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (362, 'Mathieu Henry', 'mathieu.henry362@yousign.com', 'password123', 'USER', 1285, 26.73, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (363, 'Manon Richard', 'manon.richard363@yousign.com', 'password123', 'USER', 1000, 13.04, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (364, 'Jean Michel', 'jean.michel364@yousign.com', 'password123', 'USER', 2049, 25.8, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (365, 'Mathieu Bernard', 'mathieu.bernard365@yousign.com', 'password123', 'USER', 1138, 21.54, 20, NOW());
INSERT INTO USERS (id, name, email, password, role, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (366, 'Zoe Thomas', 'zoe.thomas366@yousign.com', 'password123', 'USER', 4001, 61.2, 20, NOW());

