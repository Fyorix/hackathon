# GREENTRIP

Plateforme dédiée à la mobilité durable en entreprise. Ce projet permet aux collaborateurs de suivre, valider et convertir leurs trajets domicile-travail à vélo en un score d'impact positif concret.

## 🏗 Architecture du Projet

Le projet est structuré sous forme de monolithe modulaire pour garantir une maintenance aisée et une séparation claire des responsabilités.
- `/backend` : API Backend en Quarkus (Java 21).
  - `api/` : Endpoints REST.
  - `domain/` : Logique métier (calcul de score, validation de trajets).
  - `infra/` : Persistance (Hibernate Panache) et intégrations externes (API Strava).
- `/front` : Interface utilisateur (React/React Native).
- `/docs` : Documentation technique et schémas d'architecture.

## 🚀 Stack Technique

- Backend : Java 21, Quarkus, Hibernate Panache, RestClient.
- Infrastructure : PostgreSQL.
- Intégrations : API Strava (OAuth2), Sirene.
- Build/CI : Maven.

## 🧩 Fonctionnalités clés (MVP)
- Connectivité Strava : Synchronisation fluide des activités cyclistes via OAuth2.
- Validation intelligente : Algorithme de filtrage spatio-temporel pour isoler les trajets domicile-travail des sorties sportives.
- Score d'Impact : Calcul automatique des économies de $CO_2$ basées sur la distance parcourue
- Architecture propre : Utilisation de Mappers manuels pour isoler les entités JPA du domaine métier.

## 📖 Documentation complémentaire
Consultez le dossier `/docs` pour plus de détails :
- `docs/schemas_bdd.md` : Schémas des bases de données et relations entre entités.

## 👥 Équipe
- Victor Giroud
- Clément Pasteau
- Damien Lugagne