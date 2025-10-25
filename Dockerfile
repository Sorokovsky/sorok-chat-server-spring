# Етап 1: Компіляція проекту
FROM eclipse-temurin:25-jdk-alpine AS builder

# Встановлюємо Maven
RUN apk add --no-cache maven

# Робоча директорія
WORKDIR /app

# Копіюємо весь проект
COPY . .

# Компілюємо проект
RUN mvn clean package -DskipTests

# Етап 2: Фінальний образ для запуску
FROM eclipse-temurin:25-jre-alpine

# Створюємо користувача для безпеки
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001 -G spring

# Робоча директорія
WORKDIR /app

# Копіюємо JAR із етапу компіляції
COPY --from=builder /app/target/*.jar app.jar

# Змінюємо власника
RUN chown spring:spring app.jar
USER spring:spring

# Відкриваємо порт
EXPOSE 8080

# Запускаємо додаток
ENTRYPOINT ["java", "-jar", "app.jar"]