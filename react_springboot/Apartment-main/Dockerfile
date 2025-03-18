
# 1. ใช้ Base Image ของ Maven + JDK 17
FROM maven:3.8.5-openjdk-17 AS build

# 2. กำหนด Working Directory
WORKDIR /app

# 3. คัดลอกไฟล์ทั้งหมดไปยัง Container
COPY . .

# 4. ติดตั้ง Dependency และ Build โดยข้ามการรัน Test
RUN mvn clean package -DskipTests

# 5. ใช้ Image ใหม่เพื่อรัน Application จริง
FROM openjdk:17
WORKDIR /app

# 6. คัดลอกไฟล์ JAR ที่สร้างจาก Build Phase
COPY --from=build /app/target/*.jar app.jar

# 7. รัน Spring Boot Application
CMD ["java", "-jar", "app.jar"]

