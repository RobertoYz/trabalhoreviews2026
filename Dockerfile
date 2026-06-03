# Estágio 1: Baixar as dependências e construir o projeto
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copia o arquivo do Maven e o código fonte
COPY pom.xml .
COPY src ./src
# Compila o projeto ignorando os testes para ser mais rápido
RUN mvn clean package -DskipTests

# Estágio 2: Preparar o ambiente para rodar a API
FROM eclipse-temurin:21-jre
WORKDIR /app
# Pega o arquivo .jar gerado no passo anterior
COPY --from=build /app/target/*.jar app.jar
# Libera a porta 8080 (padrão do Spring Boot)
EXPOSE 8080
# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]