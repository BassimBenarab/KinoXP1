# KinoXP – Biograf Reservationssystem

Spring Boot-baseret system til håndtering af filmforestillinger og billetreservationer.

## Kom i gang

### Kør med Docker Compose
```bash
docker compose up --build
```
Åbn http://localhost:8080

### Admin-panel
Gå til http://localhost:8080/admin/login  
Brugernavn: `admin` | Adgangskode: `kinoxp2024`

### Kør lokalt (kræver MySQL)
```bash
./mvnw spring-boot:run
```

### Kør tests
```bash
./mvnw test
```

## Arkitektur

- **Backend**: Spring Boot 3, Spring Web, Spring Data JPA, Spring Security
- **Database**: MySQL (produktion), H2 (tests)
- **Frontend**: HTML/CSS/JavaScript (ingen frameworks)
- **CI/CD**: GitHub Actions → GHCR

## Prisregler

| Regel | Detalje |
|---|---|
| Lang film | +tillæg hvis > 170 min |
| 3D | +tillæg |
| Cowboy-rækker | -rabat (første 2 rækker) |
| Sofa-rækker | +tillæg (sidste rækker i stor sal) |
| Lille gruppe | +gebyr ved ≤ 5 billetter |
| Stor gruppe | 7% rabat på grundpris ved > 10 billetter |
