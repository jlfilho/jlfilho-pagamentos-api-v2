rm -r target
mvn clean package -DskipTests
docker compose up -d --build
