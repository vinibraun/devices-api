# 📱 Devices API

Uma API RESTful para gerenciamento de dispositivos. Ideal para uso em sistemas internos que precisam monitorar, cadastrar ou alterar o estado de equipamentos utilizados em uma organização.

## 📖 Ordem de desenvolvimento
Para organização, foi feito um checklist contendo as etapas de desenvolvimento: [Checklist](https://ticktick.com/pub/project/collaboration/invite/8725aa2447ed45948bc907d2c05fa7ab?u=412388932e1641b6b7b7329e8b7bc394)

## 🔧 Funcionalidades

- ✅ Cadastro de novos dispositivos
- ✏️ Atualização de dispositivos existentes
- 🔍 Consulta por ID, marca e estado do dispositivo
- 📋 Listagem de todos os dispositivos registrados
- 🗑️ Remoção de dispositivos, com regras de restrição por estado
- 🚫 Não permite alterar de dispositivos em uso

## 🧰 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **H2 Database** (ambiente de testes)
- **PostgreSQL** (produção, via Docker)
- **JUnit 5 / MockMvc** (testes de integração)
- **Lombok** (redução de boilerplate)
- **MapStruct** (mapeamento entre DTOs e entidades)

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="postgresql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="40" alt="docker logo"  />
</div>

###

## Observações
- A branch main serve como uma visão geral das funcionalidades e tecnologias. Para uma visão mais técnica, acesse a branch master, que contém a versão com cobertura de testes, configurações avançadas e documentação detalhada
