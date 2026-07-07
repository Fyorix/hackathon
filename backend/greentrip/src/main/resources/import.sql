-- Seed data for testing and local development
INSERT INTO COMPANIES (id, name, total_employees, total_co2_saved, created_at)
VALUES (1, 'Takima', 1, 0.0, NOW())
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;

INSERT INTO USERS (id, name, email, role, password, carbon_points_balance, total_co2_saved, company_id, created_at)
VALUES (1, 'Alex', 'alex@takima.fr', 'USER', 'password123', 120, 2.4, 1, NOW())
ON CONFLICT (id) DO UPDATE SET email = EXCLUDED.email;