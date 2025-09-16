# Plan de tâches — Système de réservation de cinéma

Objectif: Concevoir une application web Java EE/Jakarta EE (Servlets/JSP, EJB, JPA) exposant aussi une API REST, avec sécurité, permettant la réservation de places de cinéma avec gestion transactionnelle et verrouillage pour éviter les sur-réservations.

## 1) Préparation du projet et base technique

- Définir les modules et couches: Web (Servlets/JSP), EJB (services métier/transactions), JPA (entités/DAO), REST (JAX-RS), Sécurité (authn/authz).
- Vérifier `pom.xml` (versions, dépendances: Servlet/JSP, EJB, JPA, JAX-RS, CDI, H2/PostgreSQL/MySQL).
- Configurer `persistence.xml` (provider, datasource JTA, DDL auto, dialect, pool).
- Mettre à jour `web.xml` et activer CDI (`beans.xml`).


## 2) Modélisation du domaine (JPA)

- Entités: Utilisateur, Film, Salle, Séance (Film, Salle, horaire, prix), Place (rangée, numéro, salle), Réservation (user, séance, places, statut, horodatage), Ticket (optionnel).
- Contraintes et index: unicité email, clés étrangères, index sur `Seance(date_heure, salle_id)`, contrainte unique sur `Reservation(seance_id, place_id)` via table de liaison ou verrouillage.
- Stratégies: `@Version` pour verrouillage optimiste, ou verrous pessimistes (`LockModeType.PESSIMISTIC_WRITE`) sur stock/disponibilités.

## 3) Données d’amorçage (fixtures)

- Scripts d’initialisation: quelques films, salles, séances, places.
- Endpoint d’admin ou `import.sql` pour démarrage rapide.

## 4) Couche service (EJB/Transactions)

- Services: `UserService`, `FilmService`, `SeanceService`, `ReservationService`.
- Transactions:
  - Réservation: démarre transaction, vérifie disponibilités, verrouille ressources (optimiste/pessimiste), crée réservation, commit; rollback si conflit.
  - Annulation/Modification: inverse des effets, libère places.
- Isolement: configurer `@TransactionAttribute` et (si supporté) niveau d’isolement via datasource.

## 5) Authentification & Sécurité

- Inscription (hash de mot de passe: BCrypt/Argon2), login (session HTTP ou JAAS/Jakarta Security).
- Rôles: `USER`, `ADMIN`. Restrictions sur endpoints (création séances côté admin, réservations côté user).
- Filtres/Servlet Filter pour protéger ressources, CSRF sur formulaires, validation d’input côté serveur.

## 6) Interfaces Web (Servlets/JSP)

- JSP/Servlets:
  - Inscription (`/register`), Login (`/login`), Logout (`/logout`).
  - Recherche séances: liste films, sélection date, affichage disponibilités.
  - Détail séance: plan de salle, places dispo/occupées, sélection multiple.
  - Réservation: confirmation, affichage récapitulatif, état.
  - Mes réservations: lister, annuler, modifier.
- JSTL/EL, messages d’erreur, i18n minimal, pages simples mais robustes.

## 7) API REST (JAX-RS)

- Ressources:
  - `GET /api/seances?filmId=&date=`: rechercher séances.
  - `GET /api/seances/{id}/disponibilites`: places disponibles.
  - `POST /api/reservations`: créer réservation (auth requise).
  - `DELETE /api/reservations/{id}`: annuler (auth requise).
- JSON-B/Jackson, mappage DTO, gestion des erreurs (codes HTTP, messages).
- Sécurité: token de session ou Basic pour dev; idéalement JWT (optionnel selon temps).

## 8) Concurrence et verrouillage

- Choix final:
  - Option A (optimiste): champ `version` sur `Seance`/`Place`, contrôle de concurrency lors du commit; re-essai si `OptimisticLockException`.
  - Option B (pessimiste): `SELECT ... FOR UPDATE` via `LockModeType.PESSIMISTIC_WRITE` sur lignes de places pour la séance lors de la réservation.
- Tests de compétition (deux réservations concurrentes) pour valider l’absence de double attribution.

## 9) Validation et gestion des erreurs

- Validation Bean (Jakarta Validation) sur DTO et entités.
- Gestion centralisée des erreurs REST (`ExceptionMapper`) et messages JSP.

## 10) Tests

- Tests unitaires services (Mockito/Arquillian léger selon serveur).
- Tests d’intégration JPA (H2 en mémoire) pour verrouillage et contraintes.
- Tests REST (RestAssured) basiques.

## 11) Packaging & exécution

- Build Maven, profil dev (DB H2 mem)
- Script de lancement et README avec instructions.

## 12) Livrables

- Code avec commentaires concis.
- `README.md` exécution, endpoints, rôles.
- Jeu de données de démo.

---

# Découpage en tâches exécutables

1. Initialiser dépendances Maven et config `persistence.xml` (H2 + JTA/EJB).
2. Créer entités JPA: Utilisateur, Film, Salle, Seance, Place, Reservation (+ relations, @Version).
3. Mettre en place repository/DAO ou usage direct `EntityManager` dans EJB.
4. Implémenter `UserService` (inscription, login avec hash).
5. Implémenter `SeanceService` (recherche, disponibilités).
6. Implémenter `ReservationService` (réservation/annulation/modification) avec verrouillage choisi.
7. Créer Servlets/JSP: register/login/logout.
8. Créer Servlets/JSP: recherche séances, détail séance avec plan de salle, réservation.
9. Créer Servlets/JSP: gestion des réservations utilisateur.
10. Implémenter API REST (JAX-RS) pour disponibilités et réservation.
11. Sécuriser: filtres, rôles, restrictions sur URLs.
12. Ajouter fixtures de données (films, salles, séances, places).
13. Écrire tests d’intégration JPA (verrouillage), tests REST basiques.
14. Finaliser packaging, README et scripts d’exécution.

# Hypothèses techniques

- Serveur: WildFly recommandé pour EJB;
- DB par défaut: H2 en mémoire pour dev, migration facile vers PostgreSQL.
- Niveau de sécurité initial: session-based; JWT en option ultérieure.

# Points à valider (par vous)

- Choix du mécanisme de verrouillage: Optimiste (A) ou Pessimiste (B).
- Serveur d’application ciblé pour l’exécution.
- Base de données cible en prod.
- Étendue des fonctionnalités d’admin (CRUD films/salles/séances) dès V1 ou ultérieure.
