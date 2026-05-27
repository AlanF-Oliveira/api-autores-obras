# API Autores e Obras

API REST para gerenciamento de autores e obras literárias, desenvolvida com Spring Boot e PostgreSQL.

---

# Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Spring Security
- JWT (JJWT)
- PostgreSQL
- MapStruct
- Lombok
- SpringDoc OpenAPI (Swagger)
- JUnit 5
- Mockito

---

# Funcionalidades

- CRUD completo de autores
- CRUD completo de obras
- Relacionamento entre autores e obras
- Autenticação com JWT
- Registro e login de usuários
- Documentação com Swagger
- Tratamento global de exceções
- Testes unitários com JUnit e Mockito
- Conversão DTO ↔ Entity com MapStruct
- Arquitetura em camadas
- API RESTful com Spring Boot

---

# Como Executar

## Pré-requisitos

- Java 21
- PostgreSQL instalado e rodando
- Maven

---

# Configuração do Banco de Dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE db_autores_obras;
```

---

# Configuração do application.properties

```properties
spring.application.name=api-autores-obras

spring.datasource.url=jdbc:postgresql://localhost:5432/db_autores_obras
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

---

# Variáveis de Ambiente

Configure as seguintes variáveis de ambiente:

```env
JWT_SECRET=sua_chave_secreta
JWT_EXPIRATION=86400000
```

---

# Rodando a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

A documentação Swagger estará disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Segurança

A API utiliza autenticação JWT para proteger os endpoints.

## Endpoints Públicos

- POST `/auth/register`
- POST `/auth/login`

## Endpoints Protegidos

Todos os demais endpoints exigem autenticação via Bearer Token.

Exemplo:

```text
Bearer seu_token
```

---

# Autenticação

## Registrar Usuário

- **POST /auth/register**

### Body

```json
{
  "nome": "Alan",
  "email": "alan@email.com",
  "password": "123456"
}
```

### Resposta

```json
{
  "nome": "Alan",
  "email": "alan@email.com"
}
```

---

## Login

- **POST /auth/login**

### Body

```json
{
  "email": "alan@email.com",
  "password": "123456"
}
```

### Resposta

```json
{
  "token": "jwt_token"
}
```

---

# Endpoints da Aplicação

# Autores

## Buscar todos os autores

- **GET /autores**
    - Retorna a lista de autores cadastrados.
    - Resposta: `200 OK`

---

## Buscar autor por ID

- **GET /autores/{id}**
    - Retorna um autor pelo ID.
    - Resposta: `200 OK`
    - Erro: `404 Not Found` — autor não encontrado.

---

## Cadastrar autor

- **POST /autores**

### Body

```json
{
  "nome": "Machado de Assis",
  "cpf": "12345678900",
  "dataNascimento": "1839-06-21",
  "nacionalidade": "Brasileiro"
}
```

- Resposta: `201 Created`
- Erro: `400 Bad Request` — dados inválidos.

---

## Atualizar autor

- **PATCH /autores/{id}**

### Body

```json
{
  "nome": "Machado de Assis Atualizado"
}
```

- Resposta: `200 OK`
- Erro: `404 Not Found` — autor não encontrado.

---

## Remover autor

- **DELETE /autores/{id}**
    - Remove um autor pelo ID.
    - Resposta: `204 No Content`
    - Erro: `404 Not Found` — autor não encontrado.

---

# Obras

## Buscar todas as obras

- **GET /obras**
    - Retorna a lista de obras cadastradas.
    - Resposta: `200 OK`

---

## Buscar obra por ID

- **GET /obras/{id}**
    - Retorna uma obra pelo ID.
    - Resposta: `200 OK`
    - Erro: `404 Not Found` — obra não encontrada.

---

## Cadastrar obra

- **POST /obras**

### Body

```json
{
  "titulo": "Dom Casmurro",
  "descricao": "Romance brasileiro",
  "dataPublicacao": "1899-01-01",
  "autoresIds": [1]
}
```

- Resposta: `201 Created`
- Erro: `400 Bad Request` — dados inválidos.
- Erro: `404 Not Found` — autor não encontrado.

---

## Atualizar obra

- **PATCH /obras/{id}**

### Body

```json
{
  "titulo": "Dom Casmurro - Edição Revisada"
}
```

- Resposta: `200 OK`
- Erro: `404 Not Found` — obra não encontrada.

---

## Remover obra

- **DELETE /obras/{id}**
    - Remove uma obra pelo ID.
    - Resposta: `204 No Content`
    - Erro: `404 Not Found` — obra não encontrada.

---

# Testes

Para executar os testes da aplicação:

```bash
mvn test
```

Os testes utilizam:

- JUnit 5
- Mockito

---

# Estrutura do Projeto

```text
src/main/java/com/alan/api_autores_obras
├── config           # Configurações da aplicação
├── controller       # Endpoints REST
├── service          # Regras de negócio
├── repository       # Acesso ao banco de dados
├── entity           # Entidades JPA
├── dto              # Objetos de transferência de dados
│   ├── autor
│   ├── obra
│   └── auth
├── mapper           # Conversão entre DTOs e entidades (MapStruct)
├── security         # JWT, filtros e autenticação
├── exception        # Exceptions customizadas e handler global
└── test             # Testes unitários
```

---

# Padrão de Erros

Todos os erros retornam o seguinte formato JSON:

```json
{
  "status": 404,
  "mensagem": "Autor não encontrado",
  "timestamp": "27/05/2026 10:00:00"
}
```