# 💸 PIX System - Sistema de Pagamentos Instantâneos

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-yellow)

**Sistema de pagamentos instantâneos PIX desenvolvido com Spring Boot**
*Demonstração de competências técnicas em desenvolvimento Java backend*

</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
- [Documentação da API](#-documentação-da-api)
- [Banco de Dados](#-banco-de-dados)
- [Testes](#-testes)
- [Roadmap](#-roadmap)
- [Contribuição](#-contribuição)
- [Contato](#-contato)

---

## 🎯 Sobre o Projeto

O **PIX System** é um sistema de pagamentos instantâneos que simula as funcionalidades do PIX brasileiro. O projeto demonstra a implementação de um sistema financeiro robusto utilizando **arquitetura em camadas** e **boas práticas** de desenvolvimento Java com Spring Boot.

### 🎨 Contexto de Negócio
- **Gestão de Contas** digitais
- **Chaves PIX** (CPF, email, telefone, aleatória)
- **Transferências PIX** instantâneas
- **Histórico de Transações**
- **Validações de Negócio** rigorosas

### 🏗️ Evolução do Projeto

```mermaid
graph LR
    A[Monolito<br/>Estruturado] --> B[Microserviços<br/>HTTP]
    B --> C[Mensageria<br/>RabbitMQ]
    C --> D[Containerização<br/>Docker]
    D --> E[CI/CD<br/>Pipeline]
    
    style A fill:#e1f5fe
    style B fill:#f3e5f5
    style C fill:#fff3e0
    style D fill:#e8f5e8
    style E fill:#fce4ec
```

*Atualmente: **Fase 1 - Monolito Estruturado*** ✅

---

## 🏛️ Arquitetura

### 📊 Arquitetura em Camadas (Monolito Atual)

```mermaid
graph TB
    subgraph "Presentation Layer"
        C[Controllers<br/>REST Endpoints]
    end
    
    subgraph "Business Layer"
        S[Services<br/>Regras de Negócio]
        V[Validators<br/>Validações]
    end
    
    subgraph "Persistence Layer"
        R[Repositories<br/>Spring Data JPA]
        E[Entities<br/>JPA Models]
    end
    
    subgraph "Database"
        DB[PostgreSQL<br/>Dados Persistidos]
    end
    
    C --> S
    S --> V
    S --> R
    R --> E
    E --> DB
```

### 🔧 Responsabilidades por Camada

| Camada | Responsabilidade | Tecnologias |
|--------|------------------|-------------|
| **API** | Endpoints REST, DTOs, Exception Handling | Spring Web, Bean Validation |
| **Domain** | Regras de negócio, Services, Models | Spring Core, JPA |
| **Infrastructure** | Persistência, Configurações | Spring Data JPA, PostgreSQL |

---

## 🛠️ Tecnologias Utilizadas

### Core Framework
- **Java 17** - Linguagem principal
- **Spring Boot 3.5.5** - Framework web
- **Maven** - Gerenciamento de dependências

### Persistência & Banco
- **Spring Data JPA** - ORM e repositórios
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Controle de versão do banco
- **Flyway PostgreSQL** - Driver específico

### Mapeamento & Validação
- **MapStruct 1.6.3** - Mapeamento entre DTOs e Entities
- **Bean Validation** - Validações de entrada
- **Lombok** - Redução de boilerplate

### Documentação & Testes
- **SpringDoc OpenAPI 2.8.9** - Documentação Swagger
- **Spring Boot Test** - Testes integrados

### Utilitários
- **Lombok** - Geração automática de código
- **Maven Compiler Plugin** - Configuração de annotation processors

---

## ⚡ Funcionalidades

### 👥 Gestão de Contas
- ✅ Criar conta digital
- ✅ Consultar dados da conta
- ✅ Atualizar informações pessoais
- ✅ Consultar saldo em tempo real
- ✅ Histórico de movimentações

### 🔑 Chaves PIX
- ✅ Cadastrar chave PIX (CPF, email, telefone, aleatória)
- ✅ Validar formato das chaves
- ✅ Consultar chaves por conta
- ✅ Excluir chave PIX
- ✅ Evitar duplicatas

### 💸 Transferências PIX
- ✅ Transferência entre contas via chave PIX
- ✅ Validação de saldo disponível
- ✅ Confirmação instantânea
- ✅ Geração de comprovante
- ✅ Histórico completo de transações

### 🛡️ Segurança & Validação
- ✅ Validação de CPF
- ✅ Validação de formato de email
- ✅ Validação de telefone
- ✅ Prevenção de operações inválidas

---

## 📁 Estrutura do Projeto

### 🗂️ Organização de Pacotes

```
src/main/java/com/misael/pixsystem/
├── api/                           # Camada de Apresentação
│   ├── assemblers/
│   │   ├── AccountResponseAssembler.java
│   ├── controller/
│   │   ├── AccountController.java
│   │   ├── PaymentController.java
│   │   └── PixKeyController.java
│   ├── docs/
│   │   ├── AccountControllerOpenApi
│   │   ├── PaymentControllerOpenApi
│   │   └── PixKeyControllerOpenApi
│   ├── dto/
│   │   ├── request/
│   │   │   ├── AccountsRequestDTO.java
│   │   │   ├── AccountUpdateRequestDTO.java
│   │   │   ├── PaymentRequestDTO.java
│   │   │   ├── PixKeysRequestDTO.java
│   │   └── response/
│   │       ├── AccountBalanceResponseDTO.java
│   │       ├── AccountPixKeyResponseDTO.java
│   │       ├── AccountsResponseDTO.java
│   │       ├── PixKeysResponseDTO.java
│   │       ├── PixResponseResumoDTO.java
│   │       └── PixKeyResponse.java
│   └── exceptionhandler/
│       ├── ApiError.java
│       ├── ApiExceptionHandler.java
│       └── ProblemType.java
├── core/                          # Configurações
│   ├── mapper/
│   │   ├── OpenApiConfig.java
│   │   ├── OpenApiConfig.java
│   │   └── OpenApiConfig.java
│   ├── swagger/
│   │   └── SwaggerConfig.java
├── domain/                        # Regras de Negócio
│   ├── exceptions/
│   │   ├── AccountNotFoundException.java
│   │   ├── BusinessException.java
│   │   ├── EmailAlreadyExistsException.java
│   │   ├── EntityNotFoundException.java
│   │   ├── InsufficientBalanceException.java
│   │   ├── InvalidOperationException.java
│   │   ├── MaxPixKeysLimitException.java
│   │   ├── PixKeyAlreadyExistsException.java
│   │   ├── PixKeyNotFoundException.java
│   │   └── TransactionNotFoundException.java
│   ├── model/
│   │   ├── Accounts.java
│   │   ├── PixKeys.java
│   │   └── Transaction.java
│   ├── repository/
│   │   ├── AccountsRepository.java
│   │   ├── PixKeysRepository.java
│   │   └── TransactionsRepository.java
│   └── service/
│       └── impl/
│           ├── AccountServiceImpl.java
│           ├── PaymentServiceImpl.java
│           └── PixKeyServiceImpl.java
│       ├── AccountService.java
│       ├── PaymentService.java
│       └── PixKeysService.java
└── PixSystemApplication.java
```

### 📄 Arquivos de Configuração

```
src/main/resources/
├── application.yml                # Configuração principal
└── db/migration/                 # Scripts Flyway
    ├── V1__create_accounts_table.sql
    ├── V2__create_pix_keys_table.sql
    └── V3__create_transactions_table.sql
```

---

## 📋 Pré-requisitos

### 🖥️ Ambiente de Desenvolvimento
- **Java 17+** - [Download OpenJDK](https://openjdk.java.net/projects/jdk/17/)
- **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- **PostgreSQL 12+** - [Download PostgreSQL](https://www.postgresql.org/download/)
- **Git** - [Download Git](https://git-scm.com/downloads)

### 🔍 Ferramentas Recomendadas
- **IntelliJ IDEA** ou **VS Code** - IDE
- **Postman** ou **Insomnia** - Testes de API
- **DBeaver** ou **pgAdmin** - Cliente PostgreSQL

---

## 🚀 Como Executar

### 📦 1. Clone o Repositório
```bash
git clone https://github.com/seu-usuario/pix-system.git
cd pix-system
```

### 🗄️ 2. Configure o Banco de Dados
```sql
-- Conecte no PostgreSQL e execute:
CREATE DATABASE pix-system;
```

### ⚙️ 3. Configure o application.yml
```yaml
# src/main/resources/application.yml
spring:
  application:
    name: pix-system

  datasource:
    url: jdbc:postgresql://localhost:5432/${DB}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  flyway:
    enabled: true
    locations: classpath:db/migration

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

### ▶️ 4. Execute a Aplicação
```bash
# Instale as dependências e execute
mvn clean install
mvn spring-boot:run

# Ou usando o wrapper (se disponível)
./mvnw spring-boot:run
```

---

## 📚 Documentação da API

### 🌐 Swagger UI
Após executar a aplicação, acesse:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs JSON**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### 🔗 Endpoints Principais

#### Gestão de Contas
```http
POST   /api/account                    # Criar conta
GET    /api/account/{id}               # Consultar conta
GET    /api/account/{id}/balance       # Consultar saldo
PUT    /api/account/{id}               # Atualizar conta
```

#### Chaves PIX
```http
POST   /api/account/{accountId}/pix-keys  # Cadastrar chave PIX
GET    /api/account/{accountId}/pix-keys  # Listar chaves da conta
DELETE /api/pix-keys/{keyId}              # Excluir chave PIX
GET    /api/pix-keys/validate/{key}       # Validar chave PIX
```

#### Transferências PIX
```http
POST   /api/transfers/pix               # Realizar transferência PIX
GET    /api/transfers/{id}              # Consultar transferência
GET    /api/transfers/account/{accountId} # Histórico da conta
```

### 📝 Exemplos de Uso

#### Criar Nova Conta
```bash
curl -X POST http://localhost:8080/api/account \
  -H "Content-Type: application/json" \
  -d '{
          "name": "Natalia Michel",
          "cpf": "32186009013",
          "email": "nataliamichels@email.com",
          "phone": "2198888-3232"
      }'
```

#### Cadastrar Chave PIX
```bash
curl -X POST http://localhost:8080/api/account/1/pix-keys \
  -H "Content-Type: application/json" \
  -d '{
           "accountId": 2,
            "keyValue": "63962983090",
            "keyType": "CPF"
      }'
```

#### Realizar Transferência PIX
```bash
curl -X POST http://localhost:8080/api/transfers/pix \
  -H "Content-Type: application/json" \
  -d '{
        "senderId": 1,
        "receiverId": 2,
        "amount": "100.00",
        "description": "Valor do Churras"
      }'
```

---

## 🗄️ Banco de Dados

### 📊 Modelo de Dados

```mermaid
erDiagram
    ACCOUNTS {
        bigint id PK
        varchar name
        varchar cpf
        varchar email
        varchar phone
        decimal balance
        timestamp created_at
        timestamp updated_at
    }
    
    PIX_KEYS {
        bigint id PK
        bigint account_id FK
        varchar key_value
        varchar key_type
        boolean active
        timestamp created_at
    }
    
    TRANSACTIONS {
        bigint id PK
        bigint from_account_id FK
        bigint to_account_id FK
        varchar pix_key_used
        decimal amount
        varchar description
        timestamp created_at
    }
    
    ACCOUNTS ||--o{ PIX_KEYS : has
    ACCOUNTS ||--o{ TRANSACTIONS : sends
    ACCOUNTS ||--o{ TRANSACTIONS : receives
```

### 🔄 Migrations Flyway

#### V1 - Criar Tabela de Contas
```sql
-- V1__create_accounts_table.sql
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### V2 - Criar Tabela de Chaves PIX
```sql
-- V2__create_pix_keys_table.sql
CREATE TABLE pix_keys (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    key_value VARCHAR(255) UNIQUE NOT NULL,
    key_type VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
```

#### V3 - Criar Tabela de Transações
```sql
-- V3__create_transactions_table.sql
CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    pix_key_receiver VARCHAR(255),
    amount DECIMAL(15,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES account(id),
    FOREIGN KEY (receiver_id) REFERENCES account(id)
);
```

---

## 🧪 Testes

### 🔬 Estrutura de Testes
```
src/test/java/com/misael/pixsystem/
├── domain/
│   ├── service/
│   │   ├── AccountServiceTest.java
│   │   ├── PaymentServiceTest.java
│   │   └── PixKeyServiceTest.java
    └── PixSystemIntegrationTest.java
```

## 🗺️ Roadmap

### ✅ Fase 1 - Monolito (Atual)
- [x] Estrutura de projeto
- [x] Configuração Spring Boot
- [x] Modelos de dados (JPA)
- [x] Repositories e Services
- [x] Controllers REST
- [x] Validações de negócio
- [x] Documentação Swagger
- [x] Testes unitários completos

### 🔄 Fase 2 - Melhorias (Próxima)
- [ ] Cache com Redis
- [ ] Auditoria avançada
- [ ] Logs estruturados
- [ ] Health checks customizados
- [ ] Profiles de ambiente
- [ ] Docker containerization

### 🚀 Fase 3 - Microserviços (Futura)
- [ ] Separação em microserviços
- [ ] Service Discovery
- [ ] API Gateway
- [ ] Comunicação entre serviços

### 📨 Fase 4 - Mensageria (Futura)
- [ ] RabbitMQ integration
- [ ] Eventos assíncronos
- [ ] Notification service
- [ ] Event sourcing

### 🔧 Fase 5 - DevOps (Futura)
- [ ] CI/CD pipeline
- [ ] Kubernetes deployment
- [ ] Monitoramento (Prometheus)
- [ ] Centralized logging

---

## 🤝 Contribuição

### 📋 Como Contribuir

1. **Fork** o repositório
2. **Crie** uma branch: `git checkout -b feature/nova-funcionalidade`
3. **Commit** suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
4. **Push** para a branch: `git push origin feature/nova-funcionalidade`
5. **Abra** um Pull Request

### 📝 Padrões de Desenvolvimento
- **Java Code Conventions**
- **Spring Boot Best Practices**
- **Clean Code principles**
- **SOLID principles**
- **Testes unitários obrigatórios**

---

## 📞 Contato

### 👨‍💻 Desenvolvedor
**Misael**

- 📧 **Email**: [seu-email@exemplo.com]
- 💼 **LinkedIn**: [seu-linkedin]
- 🐱 **GitHub**: [seu-github]

### 🎯 Sobre este Projeto
Projeto desenvolvido para demonstrar competências em:
- ☕ **Java 17** com **Spring Boot**
- 🏗️ **Arquitetura em camadas**
- 🗄️ **Persistência** com **JPA/PostgreSQL**
- 📚 **Documentação** com **Swagger**
- 🧪 **Testes** automatizados
- 💳 **Sistemas financeiros**

---

<div align="center">

**💸 Desenvolvido com dedicação e paixão por tecnologia 💸**

*Sistema PIX - Pagamentos Instantâneos do Futuro* 🚀

</div>

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
