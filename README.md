# ✈️ Aéroport - Backend Microservices

Système de gestion aéroportuaire basé sur une architecture microservices Spring Boot, avec migration vers la **Clean Architecture** (Hexagonal) pour les services métier.

## Architecture globale

```
                         Client (React)
                               │
                               ▼
                      ┌────────────────┐
                      │  API Gateway   │  ← JWT Filter + CORS
                      │   port 8080    │
                      └───────┬────────┘
                              │
                      ┌───────▼────────┐
                      │     Eureka     │  ← Service Discovery
                      │   port 8761    │
                      └───────┬────────┘
                              │
        ┌─────────┬───────────┼───────────┬──────────┐
        ▼         ▼           ▼           ▼          ▼
   service-   service-   service-    service-     Kafka
     auth       vols    reservations notifications (async)
   :8081      :8082       :8083       :8084      :9092
     │          │           │           │
     ▼          ▼           ▼           ▼
   PostgreSQL PostgreSQL PostgreSQL  PostgreSQL
   :5431      :5432       :5433       :5434
```

## Stack technique

- **Java 17** (compilé avec Maven 3.9, runtime Eclipse Temurin 21)
- **Spring Boot 4.0.2** + **Spring Cloud 2025.1.0**
- **Spring Security** + **JWT** (JJWT 0.11.5)
- **PostgreSQL 15** (une base par microservice)
- **Apache Kafka** (Confluent 7.5.0, mode KRaft sans Zookeeper)
- **Docker Compose** (infrastructure + services)
- **Lombok** + **Maven**

--- 

## Structure du projet

```
Aeroport-Clean/
├── eureka/                   # Service Discovery (port 8761)
├── gateway/                  # API Gateway (port 8080)
├── service-auth/             # Authentification + JWT (port 8081) - Architecture classique
├── service-vols/             # Gestion des vols (port 8082) - Clean Architecture
├── service-reservations/     # Gestion des réservations (port 8083) - Clean Architecture
├── service-notifications/    # Notifications Kafka (port 8084) - Clean Architecture
├── k8s/                      # Manifestes Kubernetes (30 fichiers)
├── docker-compose.yml        # Dev (build local)
└── Jenkinsfile               # CI/CD : GitHub → Docker Hub → AWS EC2
```

---

## Clean Architecture

Les services **vols**, **réservations** et **notifications** suivent la Clean Architecture (ports & adapters) :

```
src/main/java/com/aeroport/{service}/
├── domain/
│   ├── model/             # Entités métier pures (POJO + Lombok, aucune annotation framework)
│   ├── port/in/           # Ports entrants (interfaces use cases)
│   ├── port/out/          # Ports sortants (interfaces repository, event publisher)
│   └── exception/         # Exceptions métier typées
├── application/           # Implémentation des use cases (wirés via @Bean, pas de @Service)
├── infrastructure/
│   ├── persistence/       # @Entity JPA, Spring Data repos, mappers domain↔JPA, adapters
│   ├── kafka/             # Kafka event DTOs + adapters (impl des ports out)
│   ├── feign/             # @FeignClient + adapters (réservations, notifications)
│   └── config/            # @Configuration + @Bean (composition root : BeanConfig.java)
├── presentation/          # @RestController, DTOs request/response, mappers, exception handlers
└── *Application.java
```

**Règle de dépendance** : `presentation / infrastructure → application → domain`
Le domain ne dépend d'aucun framework.

### Service Auth (architecture classique)

```
src/main/java/com/aeroport/auth/
├── controller/        # @RestController
├── service/           # Logique métier
├── entity/            # @Entity JPA + enums
├── repository/        # Spring Data JPA
├── dto/               # DTOs request/response
├── config/            # @Configuration Spring
├── security/          # JWT (génération + validation)
└── ServiceAuthApplication.java
```

---

## Lancement rapide

### Prérequis

- Docker + Docker Compose

### Lancement

```bash
docker-compose up -d --build
```

### Vérification

- Eureka Dashboard : http://localhost:8761
- API Gateway : http://localhost:8080

---

## Docker Hub

| Service | Image |
|---------|-------|
| Eureka | `moboks/aeroport-eureka:v1` |
| Gateway | `moboks/aeroport-gateway:v1` |
| Auth | `moboks/aeroport-service-auth:v1` |
| Vols | `moboks/aeroport-service-vols:v1` |
| Reservations | `moboks/aeroport-service-reservations:v1` |
| Notifications | `moboks/aeroport-service-notifications:v1` |

---

## Services

### Eureka Server (port 8761)

Service Discovery — annuaire central où tous les microservices s'enregistrent.

### API Gateway (port 8080)

Point d'entrée unique :
- Routage des requêtes vers les services
- Validation JWT (filtre personnalisé)
- Extraction des infos utilisateur (headers `X-User-Id`, `X-User-Role`)
- Gestion CORS

**Routes :**

| Prefix | Service cible |
|--------|---------------|
| `/auth/**` | SERVICE-AUTH |
| `/vols/**` | SERVICE-VOLS |
| `/reservations/**` | SERVICE-RESERVATIONS |
| `/notifications/**` | SERVICE-NOTIFICATIONS |

### Service Auth (port 8081)

Authentification et inscription.

**Entités :** `User` (id, email, password, nom, prenom, role)

**Enum Role :** `PASSAGER`, `ADMIN`

**Endpoints :**

| Méthode | URL | Description |
|---------|-----|-------------|
| POST | `/auth/register` | Inscription |
| POST | `/auth/login` | Connexion (retourne JWT) |

### Service Vols (port 8082) — Clean Architecture

Gestion des vols.

**Modèle domaine :** `Vol` (id, numeroVol, origine, destination, dateDepart, dateArrivee, placesDisponibles, prixBase, compagnie, statut)

**Enum Statut :** `A_L_HEURE`, `RETARDE`, `ANNULE`

**Endpoints :**

| Méthode | URL | Accès | Description |
|---------|-----|-------|-------------|
| GET | `/vols` | Public | Liste tous les vols |
| GET | `/vols/{id}` | Public | Détail d'un vol |
| GET | `/vols/destination/{destination}` | Public | Vols par destination |
| GET | `/vols/{id}/places-disponibles` | Public | Places restantes |
| POST | `/vols` | Admin | Créer un vol |
| PUT | `/vols/{id}` | Admin | Modifier un vol |
| PUT | `/vols/{id}/statut` | Admin | Changer le statut |
| DELETE | `/vols/{id}` | Admin | Supprimer un vol |

**Ports sortants :** `VolRepositoryPort`, `VolEventPublisherPort`

**Kafka Producer :** Publie sur le topic `vol-events` lors de la modification ou du changement de statut.

### Service Réservations (port 8083) — Clean Architecture

Gestion des réservations avec vérification des places disponibles via Feign.

**Modèle domaine :** `Reservation` (id, passagerId, volId, dateReservation, statut, nombrePlaces)

**Enum Statut :** `EN_ATTENTE`, `CONFIRMEE`, `ANNULEE`

**Endpoints :**

| Méthode | URL | Accès | Description |
|---------|-----|-------|-------------|
| POST | `/reservations` | Authentifié | Créer une réservation |
| GET | `/reservations/{id}` | Authentifié | Détail réservation |
| PUT | `/reservations/{id}/confirmer` | Authentifié | Confirmer |
| PUT | `/reservations/{id}/annuler` | Authentifié | Annuler |
| GET | `/reservations/passager/{id}` | Authentifié | Mes réservations |
| GET | `/reservations/vol/{volId}` | Admin | Réservations d'un vol |

**Ports sortants :** `ReservationRepositoryPort`, `ReservationEventPublisherPort`, `VolServicePort` (Feign)

**Communication :**
- **Feign Client** vers SERVICE-VOLS : vérification places, incrément/décrément places
- **Kafka Producer** : publie sur `reservation-events`
- **Kafka Consumer** : écoute `vol-events` pour annulation en cascade si un vol est annulé

### Service Notifications (port 8084) — Clean Architecture

Notifications aux passagers, alimentées par Kafka.

**Modèle domaine :** `Notification` (id, passagerId, volId, message, dateCreation, lue)

**Endpoints :**

| Méthode | URL | Accès | Description |
|---------|-----|-------|-------------|
| POST | `/notifications` | Admin | Créer une notification |
| GET | `/notifications/passager/{id}` | Authentifié | Mes notifications |
| GET | `/notifications/vol/{volId}` | Admin | Notifications d'un vol |
| PUT | `/notifications/{id}/lue` | Authentifié | Marquer comme lue |
| DELETE | `/notifications/{id}` | Authentifié | Supprimer |

**Ports sortants :** `NotificationRepositoryPort`

**Kafka Consumer :** Écoute les topics `vol-events` et `reservation-events` pour créer automatiquement des notifications.

---

## Communication inter-services

### Synchrone (Feign Client)

```
Service Réservations ──► Service Vols
   - Vérifier places disponibles
   - Incrémenter/décrémenter places
```

En Clean Architecture, le Feign client est caché derrière un port out (`VolServicePort`).

### Asynchrone (Kafka)

```
Service Vols ──────────► vol-events ──────────┐
                                              ├──► Service Notifications
Service Réservations ──► reservation-events ──┘
                                              └──► Service Réservations
                                                   (annulation auto si vol annulé)
```

**Topics Kafka :**
- `vol-events` : modifications de vols, changements de statut
- `reservation-events` : création, confirmation, annulation de réservations

En Clean Architecture, Kafka est caché derrière des ports out (`VolEventPublisherPort`, `ReservationEventPublisherPort`).

---

## Sécurité

### Approche Gateway-centric

```
Client → [JWT dans Authorization header] → Gateway
  → Validation JWT
  → Extraction userId + role
  → Headers X-User-Id, X-User-Role → Services métier
```

Les services métier ne valident pas le JWT — ils font confiance au Gateway.

### Routes publiques

- `POST /auth/**` (login, register)
- `GET /vols/**` (consultation)

### Routes protégées

- **Authentifié** : réservations passager, notifications passager
- **Admin** : CRUD vols (POST/PUT/DELETE), notifications par vol, réservations par vol

---

## Bases de données

| Service | Conteneur | Port | Base |
|---------|-----------|------|------|
| service-auth | postgres-auth | 5431 | auth_db |
| service-vols | postgres-vols | 5432 | vols_db |
| service-reservations | postgres-reservations | 5433 | reservations_db |
| service-notifications | postgres-notifications | 5434 | notifications_db |

DDL : `spring.jpa.hibernate.ddl-auto=update` (auto-création du schéma).

---

## Déploiement

### CI/CD (Jenkins)

Pipeline déclenchée par push GitHub (branche main) :
1. Clone depuis `github.com/modibo-26/Aeroport.git`
2. Build Docker Compose + tag + push vers Docker Hub
3. SSH vers AWS EC2, pull + redeploy

### Kubernetes

```bash
kubectl apply -f k8s/
```

30 manifestes : deployments, services, PVCs pour tous les services + bases de données + Kafka.

---

## Test rapide

```bash
# Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123456","nom":"Dupont","prenom":"Jean"}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123456"}'

# Liste des vols (public)
curl http://localhost:8080/vols

# Créer un vol (admin + token)
curl -X POST http://localhost:8080/vols \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"numeroVol":"AF123","origine":"Paris","destination":"New York","dateDepart":"2026-02-01T10:00:00","dateArrivee":"2026-02-01T18:00:00","placesDisponibles":150,"prixBase":450.0,"compagnie":"Air France"}'
```

---

## Frontend

Le backend est consommé par un frontend React :

| Framework | Repo | Port | UI Library | Docker Hub |
|-----------|------|------|------------|------------|
| React | [Aeroport-Front](https://github.com/modibo-26/Aeroport-Front) | 3000 | Material UI | `moboks/aeroport-frontend:v1` |