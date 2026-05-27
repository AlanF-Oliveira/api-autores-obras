# API Autores e Obras

API REST para gerenciamento de autores e obras literárias, desenvolvida com Spring Boot e PostgreSQL.

---

## Tecnologias Utilizadas

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

## Funcionalidades

- CRUD completo de autores
- CRUD completo de obras
- Relacionamento ManyToMany entre autores e obras
- Autenticação com JWT
- Registro e login de usuários
- Documentação com Swagger
- Tratamento global de exceções
- Testes unitários com JUnit 5 e Mockito
- Conversão DTO ↔ Entity com MapStruct
- Arquitetura em camadas

---

## Pré-requisitos

- Java 21
- PostgreSQL instalado e rodando
- Maven

---

## Configuração do Banco de Dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE db_autores_obras;
```

---

## Configuração do application.properties

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

## Variáveis de Ambiente

Configure as seguintes variáveis de ambiente:

| Variável | Descrição | Exemplo |
|---|---|---|
| JWT_SECRET | Chave secreta Base64 para assinar o token | `ZAEmXRLIqQ0...` |
| JWT_EXPIRATION | Tempo de expiração em milissegundos | `86400000` (24h) |

Para gerar uma chave segura:

```bash
openssl rand -base64 32
```

---

## Rodando a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

A documentação Swagger estará disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Segurança

A API utiliza autenticação JWT. Endpoints protegidos exigem o token no header:

```
Authorization: Bearer seu_token
```

### Endpoints Públicos

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/auth/cadastrar` | Registra um novo usuário |
| POST | `/auth/entrar` | Realiza login e retorna o token |

### Endpoints Protegidos

Todos os demais endpoints exigem autenticação via Bearer Token.

---

## Endpoints da Aplicação

### Auth

#### Cadastrar Usuário
- **POST** `/auth/cadastrar`

**Body:**
```json
{
  "nome": "Alan Ferreira",
  "email": "alan@email.com",
  "password": "123456"
}
```

**Resposta:** `201 Created`
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

#### Login
- **POST** `/auth/entrar`

**Body:**
```json
{
  "email": "alan@email.com",
  "password": "123456"
}
```

**Resposta:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Autor

#### Listar todos os autores
- **GET** `/autor`
- Resposta: `200 OK`

---

#### Buscar autor por ID
- **GET** `/autor/{id}`
- Resposta: `200 OK`
- Erro: `404 Not Found`

---

#### Cadastrar autor
- **POST** `/autor`

**Body:**
```json
{
  "nome": "Machado de Assis",
  "sexo": "MASCULINO",
  "email": "machado@assis.com",
  "dataNascimento": "1839-06-21",
  "paisDeOrigem": "Brasil",
  "cpf": "123.456.789-00",
  "obrasId": []
}
```

> **Obs:** CPF é obrigatório para autores do Brasil.

- Resposta: `201 Created`
- Erro: `400 Bad Request`

---

#### Atualizar autor
- **PATCH** `/autor/{id}`

**Body (todos os campos opcionais):**
```json
{
  "nome": "Machado de Assis",
  "sexo": "MASCULINO",
  "email": "machado@assis.com",
  "dataNascimento": "1839-06-21",
  "paisDeOrigem": "Brasil",
  "cpf": "123.456.789-00",
  "obrasId": []
}
```

- Resposta: `200 OK`
- Erro: `404 Not Found`

---

#### Deletar autor
- **DELETE** `/autor/{id}`
- Resposta: `204 No Content`
- Erro: `404 Not Found`

---

### Obra

#### Listar todas as obras
- **GET** `/obra`
- Resposta: `200 OK`

---

#### Buscar obra por ID
- **GET** `/obra/{id}`
- Resposta: `200 OK`
- Erro: `404 Not Found`

---

#### Cadastrar obra
- **POST** `/obra`

**Body:**
```json
{
  "nome": "Dom Casmurro",
  "descricao": "Romance realista brasileiro",
  "dataPublicacao": "1899-01-01",
  "dataExposicao": null,
  "autorId": [1]
}
```

> **Obs:** `dataPublicacao` ou `dataExposicao` — ao menos uma é obrigatória.

- Resposta: `201 Created`
- Erro: `400 Bad Request`

---

#### Atualizar obra
- **PATCH** `/obra/{id}`

**Body (todos os campos opcionais):**
```json
{
  "nome": "Dom Casmurro",
  "descricao": "Romance realista brasileiro",
  "dataPublicacao": "1899-01-01",
  "dataExposicao": null,
  "autorId": [1]
}
```

- Resposta: `200 OK`
- Erro: `404 Not Found`

---

#### Deletar obra
- **DELETE** `/obra/{id}`
- Resposta: `204 No Content`
- Erro: `404 Not Found`

---

## Padrão de Erros

Todos os erros retornam o seguinte formato:

```json
{
  "status": 404,
  "mensagem": "Autor não encontrado",
  "timestamp": "27/05/2026 10:00:00"
}
```

---

## Testes

Para executar os testes:

```bash
mvn test
```

Os testes cobrem as camadas **Service** e **Controller** utilizando JUnit 5 e Mockito.

---

## Estrutura do Projeto

```
src/main/java/com/alan/api_autores_obras
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
│   └── config
└── exception        # Exceptions customizadas e handler global
```