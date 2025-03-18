# ใช้ Base Image ของ JDK 17
FROM openjdk:17 AS build

# กำหนด Working Directory
WORKDIR /app

# คัดลอกไฟล์โปรเจกต์ทั้งหมดไปยัง Container
COPY . .

# ให้สิทธิ์ mvnw และติดตั้ง Dependencies
RUN chmod +x ./mvnw && ./mvnw clean install -DskipTests

# ใช้ Image ใหม่เพื่อรัน Application จริง
FROM openjdk:17
WORKDIR /app

# คัลอก JAR ที่ Build มาใช้ใน Container
COPY --from=build /app/target/*.jar app.jar

# รั Application
CMD ["java", "-jar", "app.jar"]
