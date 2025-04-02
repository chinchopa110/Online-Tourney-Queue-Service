# 🏆 Сервис управления онлайн очередью для турнира по шахматам ♟️

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)](https://kafka.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![REST API](https://img.shields.io/badge/REST_API-FF6F61?style=for-the-badge&logo=json&logoColor=white)](https://en.wikipedia.org/wiki/REST)

## 🎯 Описание

🔹 **API Gateway** для получения следующего игрока из очереди (Kafka)  
🔹 Сопоставление игрока с его карточкой участника (PostgreSQL)  
🔹 Асинхронная обработка очереди для максимальной производительности

## 📡 API Endpoints

### 🎮 Получить следующего игрока в очереди
#### GET /api/persons  
**✅Пример успешного ответа:**
```json
{
  "status": "success",
  "data": {
    "person": {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "name": "Magnus Carlsen",
      "age": 32,
      "email": "magnus@chess.com"
    },
    "card": {
      "id": "987f6543-e21b-42d3-b456-426614174000",
      "personId": "123e4567-e89b-12d3-a456-426614174000",
      "elo": 2850,
      "federation": "NOR",
      "createdAt": "2025-04-02T12:34:56.789"
    }
  }
}
```
**🔄Очередь пуста:**
```json
{
    "status": "empty",
    "message": "Очередь пуста"
}
```
**❌Ошибка сервера:**
```json
{
    "status": "error",
    "message": "Internal server error"
}
```

