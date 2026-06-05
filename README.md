# Budget API

REST API do zarządzania budżetem osobistym — śledzenie przychodów i wydatków przypisanych do kont.

## Wymagania

- Java 21+
- Docker Desktop

## Uruchomienie

1. Uruchom bazę danych:
```
docker compose up -d
```

2. Uruchom aplikację:
```
./mvnw spring-boot:run
```

Aplikacja działa pod `http://localhost:8080`

## Endpointy

**Konta**
- `GET /accounts` — lista kont
- `POST /accounts` — utwórz konto
- `GET /accounts/{id}` — szczegóły konta
- `DELETE /accounts/{id}` — usuń konto

**Transakcje**
- `GET /transactions` — lista transakcji (opcjonalne filtry: `?from=`, `?to=`, `?category=`)
- `POST /transactions` — dodaj transakcję
- `DELETE /transactions/{id}` — usuń transakcję

**Podsumowanie**
- `GET /summary` — łączne przychody, wydatki, wydatki per kategoria