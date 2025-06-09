# ReservationService - Configuração Local

## 🔧 Setup do Banco de Dados

1. **Copie o arquivo de exemplo:**
   ```bash
   cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
   ```

2. **Edite suas credenciais:**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ReservationDb
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

3. **Crie o banco de dados:**
   ```sql
   CREATE DATABASE ReservationDb;
   ```

## ℹ️ Informações do Serviço

- **Porta:** 8094
- **Banco:** ReservationDb
- **Eureka:** Registrado como `xalface-reservationservice`
- **Endpoints:** Gerenciamento de reservas de salas

## 🚀 Como Executar

```bash
cd xAlface_ReservationService
mvn spring-boot:run
```

Ou use o profile local:
```bash
mvn spring-boot:run -Dspring.profiles.active=local
```
