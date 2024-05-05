# How to Run
1. Build **publisher** microservice:
```bash
cd publisher
./gradlew buildFatJar
```
2. Build **discussion** microservice:
```bash
cd discussion
./gradlew buildFatJar
```
3. Run Docker Compose: 
```bash
docker compose up
```