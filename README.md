# resCinema

Application de réservation de cinéma (Jakarta EE 10, EJB/JPA/Servlets/JSP, JAX-RS) pour WildFly + H2 (ExampleDS).

## Prérequis

- JDK 21
- Maven 3.9+
- WildFly (datasource `ExampleDS` H2 activée par défaut)

## Build

```
mvn clean package
```

Le WAR est généré sous `target/resCinema-1.0-SNAPSHOT.war`.

## Déploiement (WildFly)

- D démarrer WildFly
- Déployer le WAR via l'admin console ou `jboss-cli`.
- Accéder: `http://localhost:8080/resCinema/` 

## Comptes de démo

- Admin: `admin@cinema.local` / `admin123`
- Utilisateur: `user@cinema.local` / `user123`

## Endpoints REST

- `GET /resCinema/api/seances?filmId=&date=YYYY-MM-DD`
- `GET /resCinema/api/seances/{id}/disponibilites`
- `POST /resCinema/api/reservations` (JSON)
- `DELETE /resCinema/api/reservations/{id}`

## Notes sur la concurrence

- Verrouillage pessimiste avec timeout (5s) via `jakarta.persistence.lock.timeout` dans `persistence.xml`.
- Contrainte d’unicité sur `reserved_seats(seance_id,row_number,seat_number)` pour empêcher la double attribution.

## Sécurité

- Sessions HTTP, filtre protégeant `/app/*` et `/admin/*` (rôle ADMIN requis pour admin).

n'oubliez pas de mettre à jour les element du http-test, que ce soit le port et les autre chose 