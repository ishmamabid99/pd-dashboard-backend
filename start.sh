mvn clean package -DskipTests
docker-compose build
docker-compose up java_app