# 📡 Spécifications de l'API REST — Challenge RSE & Mobilité

Ce document détaille les points d'entrée (endpoints) de l'API REST pour la plateforme de mobilité durable et d'éco-score. Il est conçu pour être utilisé conjointement avec le schéma de la base de données décrit dans [schemas_bdd.md](file:///home/fyorix/EPITA/ING2/Projects/Hackathon/hackathon/docs/schemas_bdd.md).

---

## ⚙️ Configuration Globale

- **Format des données** : Toutes les requêtes et réponses utilisent le format `JSON`.
- **En-têtes globaux** :
  - `Content-Type: application/json`
  - `Authorization: Bearer <JWT_TOKEN>` (pour tous les points d'accès sécurisés).
- **Format des dates** : ISO 8601 (`YYYY-MM-DDTHH:mm:ssZ`).

---

## 🔄 Flux d'Interaction (Cycle de vie)

Le diagramme suivant montre la cinématique lors de la déclaration d'un trajet et son impact sur les scores individuels et collectifs.

```mermaid
sequenceDiagram
    autonumber
    actor Salarié as Salarié (Front-end)
    participant API as API Gateway / Backend
    database BDD as Base de données (PostgreSQL)

    Salarié->>API: GET /api/users/me
    API->>BDD: Récupération du profil
    BDD-->>API: Données Utilisateur
    API-->>Salarié: Profil & Solde de points actuels (200 OK)

    Salarié->>API: POST /api/trips { "distance_km": 12.0, "type": "VELO" }
    Note over API: Calcul du CO2 évité (12 * 0.20 = 2.4kg)<br/>Calcul des points gagnés (12 * 10 = 120 points)
    API->>BDD: Enregistrer le trajet & Mettre à jour l'utilisateur (points & CO2)
    API->>BDD: Mettre à jour les stats de l'entreprise (total_km, total_co2, total_points)
    BDD-->>API: Succès de la transaction
    API-->>Salarié: Détails du trajet créé (201 Created)

    Salarié->>API: GET /api/companies/me
    API->>BDD: Récupération de l'impact global de l'entreprise
    BDD-->>API: Stats Entreprise
    API-->>Salarié: Dashboard collectif & Badge débloqué (200 OK)
```

---

## 📑 Référentiel des Données

### 🚴 Types de Transport (`type`)
Le tableau ci-dessous définit les coefficients de conversion utilisés par le backend pour calculer les gains d'un trajet déclaré.

| Type | Description | CO2 Évité (kg / km) | Points Gagnés / km |
| :--- | :--- | :--- | :--- |
| `VELO` | Vélo classique ou à assistance électrique | `0.20` | `10` |
| `TROTINETTE` | Trottinette électrique | `0.15` | `8` |
| `MARCHE` | Marche à pied / course | `0.25` | `12` |
| `COVOITURAGE` | Trajet partagé en voiture | `0.10` | `5` |
| `TRANSPORTS_COMMUNS` | Métro, Tramway, Bus, RER | `0.08` | `4` |

### 👤 Rôles Utilisateurs (`role`)
- `USER` : Salarié standard (déclare des trajets, consulte ses statistiques, commande des récompenses).
- `COMPANY_ADMIN` : Représentant RSE (accède aux statistiques globales et historiques de l'entreprise).
- `SYSTEM_ADMIN` : Administrateur général de la plateforme (gestion des récompenses, des entreprises et des utilisateurs).

---

## 🚨 Format des Erreurs Standards

Toutes les erreurs renvoyées par l'API respectent le format JSON standardisé suivant :

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Description explicite de l'erreur (ex: Le type de transport renseigné n'est pas valide).",
  "timestamp": "2026-07-07T10:53:21Z"
}
```

---

## 🚴 Catégorie 1 : Mobilité & Éco-Score (Espace Salarié)

### 1. Obtenir son profil connecté
*   **Méthode / Chemin** : `GET /api/users/me`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Récupère les informations de l'utilisateur connecté pour afficher son profil, son rôle, son solde de points carbone et son cumul individuel de CO2 économisé sur le dashboard.

#### Réponses
*   **`200 OK`** : Profil récupéré avec succès.
    ```json
    {
      "id": 1,
      "name": "Alex",
      "email": "alex@takima.fr",
      "role": "USER",
      "carbon_points_balance": 450,
      "total_co2_saved": 9.2
    }
    ```
*   **`401 Unauthorized`** : Token manquant ou invalide.

#### Exemple de test
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 2. Consulter son historique de trajets
*   **Méthode / Chemin** : `GET /api/trips`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Récupère l'historique paginé des trajets de l'utilisateur connecté, triés du plus récent au plus ancien.

#### Paramètres de Requête (Query)
| Paramètre | Type | Valeur par défaut | Description |
| :--- | :--- | :--- | :--- |
| `page` | `Integer` | `0` | Numéro de la page demandée (indexé à 0). |
| `size` | `Integer` | `10` | Nombre d'éléments par page. |

#### Réponses
*   **`200 OK`** : Historique récupéré avec succès.
    ```json
    {
      "content": [
        {
          "id": 12,
          "distance_km": 15.0,
          "co2_saved": 3.0,
          "points_earned": 150,
          "type": "VELO",
          "created_at": "2026-07-07T08:30:00Z"
        },
        {
          "id": 11,
          "distance_km": 4.2,
          "co2_saved": 0.84,
          "points_earned": 42,
          "type": "TROTINETTE",
          "created_at": "2026-07-06T17:15:00Z"
        }
      ],
      "totalElements": 2,
      "totalPages": 1,
      "currentPage": 0
    }
    ```

#### Exemple de test
```bash
curl -X GET "http://localhost:8080/api/trips?page=0&size=10" \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 3. Déclarer un nouveau trajet
*   **Méthode / Chemin** : `POST /api/trips`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Déclare un trajet fraîchement effectué. Cette action calcule et met à jour instantanément les compteurs de l'utilisateur ainsi que les statistiques collectives de son entreprise.

#### Corps de la requête (Request Body)
```json
{
  "distance_km": 12.0,
  "type": "VELO"
}
```

##### Validation des champs
- `distance_km` : `Float` obligatoirement supérieur à `0.0`.
- `type` : `String` correspondant à l'un des types autorisés (voir [Types de Transport](#-types-de-transport-type)).

#### Réponses
*   **`201 Created`** : Trajet enregistré et scores incrémentés avec succès.
    ```json
    {
      "id": 13,
      "distance_km": 12.0,
      "co2_saved": 2.4,
      "points_earned": 120,
      "type": "VELO",
      "created_at": "2026-07-07T10:45:00Z"
    }
    ```
*   **`400 Bad Request`** : Données invalides (format incorrect, distance <= 0, type de transport inconnu).

#### Exemple de test
```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"distance_km": 12.0, "type": "VELO"}'
```

---

## 📊 Catégorie 2 : Compétition & RSE (Espace Entreprise)

### 4. Obtenir les statistiques collectives de son entreprise
*   **Méthode / Chemin** : `GET /api/companies/me`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Récupère les statistiques consolidées de l'entreprise à laquelle appartient l'utilisateur connecté (permet d'afficher le bandeau de l'impact collectif de la boîte).

#### Réponses
*   **`200 OK`** : Statistiques récupérées.
    ```json
    {
      "id": 3,
      "name": "Takima",
      "total_employees": 50,
      "total_co2_saved": 145.2,
      "total_km": 726.0,
      "total_points": 7260.0,
      "unlocked_badge": "🏆 Top Green 2026"
    }
    ```

#### Exemple de test
```bash
curl -X GET http://localhost:8080/api/companies/me \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 5. Consulter le classement général (Leaderboard)
*   **Méthode / Chemin** : `GET /api/leaderboard`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Récupère le classement public de toutes les entreprises partenaires, trié par le meilleur score par employé (`total_co2_saved / total_employees`).

#### Réponses
*   **`200 OK`** : Classement retourné sous forme de liste ordonnée.
    ```json
    [
      {
        "name": "Takima",
        "total_co2_saved": 145.2,
        "score_par_employe": 2.9,
        "badge": "🏆 Top Green 2026"
      },
      {
        "name": "VéloFlex (Votre Boîte)",
        "total_co2_saved": 9.2,
        "score_par_employe": 1.8,
        "badge": null
      },
      {
        "name": "EPITA",
        "total_co2_saved": 1200.0,
        "score_par_employe": 1.2,
        "badge": null
      }
    ]
    ```

#### Exemple de test
```bash
curl -X GET http://localhost:8080/api/leaderboard \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 🎁 Catégorie 3 : Boutique RSE & Récompenses (Nouveau)

> [!NOTE]
> Cette section documente les interactions avec la boutique de récompenses écologiques, permettant aux salariés de convertir leurs points durement acquis en cadeaux ou dons d'association (par exemple : gourdes en inox, kits de réparation de vélo, dons à des associations de reforestation).

### 6. Lister les récompenses disponibles
*   **Méthode / Chemin** : `GET /api/rewards`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Liste l'ensemble des récompenses proposées dans la boutique, avec leur coût en points carbone et le stock disponible.

#### Réponses
*   **`200 OK`** : Liste des récompenses récupérée.
    ```json
    [
      {
        "id": 1,
        "title": "Gourde Takima en Inox",
        "description": "Une gourde durable de 750ml double paroi pour garder vos boissons chaudes ou froides.",
        "points_cost": 300,
        "stock_available": 14
      },
      {
        "id": 2,
        "title": "Kit réparation vélo",
        "description": "Un kit de secours comprenant des rustines, démonte-pneus et un multi-outil.",
        "points_cost": 150,
        "stock_available": 45
      },
      {
        "id": 3,
        "title": "Plantation d'un arbre",
        "description": "Faites un don de vos points à Reforest'Action pour planter un arbre en France.",
        "points_cost": 100,
        "stock_available": 9999
      }
    ]
    ```

#### Exemple de test
```bash
curl -X GET http://localhost:8080/api/rewards \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 7. Commander une récompense (Créer une commande)
*   **Méthode / Chemin** : `POST /api/orders`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Permet à l'utilisateur de commander une récompense. Cette action vérifie que l'utilisateur a un solde suffisant de points carbone, débite les points, décrémente le stock de la récompense et crée une commande.

#### Corps de la requête (Request Body)
```json
{
  "reward_id": 1
}
```

#### Réponses
*   **`201 Created`** : Commande enregistrée avec succès, solde débité.
    ```json
    {
      "id": 42,
      "user_id": 1,
      "reward_id": 1,
      "points_spent": 300,
      "status": "COMPLETED",
      "created_at": "2026-07-07T10:52:00Z"
    }
    ```
*   **`400 Bad Request`** : Points insuffisants, stock épuisé ou identifiant de récompense invalide.
    ```json
    {
      "status": 400,
      "error": "Bad Request",
      "message": "Solde de points insuffisant (Solde actuel: 150, Coût requis: 300).",
      "timestamp": "2026-07-07T10:52:05Z"
    }
    ```

#### Exemple de test
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"reward_id": 1}'
```

---

### 8. Consulter l'historique de ses commandes
*   **Méthode / Chemin** : `GET /api/orders`
*   **Authentification** : Requise (`Bearer Token`)
*   **Description** : Liste l'historique des commandes passées par l'utilisateur connecté.

#### Réponses
*   **`200 OK`** : Liste des commandes récupérée.
    ```json
    [
      {
        "id": 42,
        "reward": {
          "id": 1,
          "title": "Gourde Takima en Inox"
        },
        "points_spent": 300,
        "status": "COMPLETED",
        "created_at": "2026-07-07T10:52:00Z"
      }
    ]
    ```

#### Exemple de test
```bash
curl -X GET http://localhost:8080/api/orders \
  -H "Authorization: Bearer <TOKEN>"
```
