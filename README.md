# Devices API – Documentação Técnica
Esta branch contém os detalhes técnicos da implementação da Devices API, incluindo decisões arquiteturais, padrões aplicados, desafios enfrentados e estratégias de desenvolvimento.

## Visão Geral
A Devices API foi projetada como uma solução RESTful para o gerenciamento de dispositivos. O objetivo foi criar uma base sólida, extensível e testável, aplicando boas práticas de engenharia de software.

## Arquitetura e Padrões
- Arquitetura em camadas: controller, service, repository, dto, mapper e entity.

- Separação clara entre entidades de persistência e objetos de transporte (DTOs).

- Padrão DTO + MapStruct para abstrair a lógica de conversão entre modelos de domínio e representação externa.

- Validações com Jakarta Bean Validation via anotações nas classes DTO.

- Testes de integração com MockMvc e banco de dados H2 em ambiente isolado.

- Configuração de profiles para ambiente de desenvolvimento (H2) e produção (PostgreSQL via Docker).

## Tecnologias e Ferramentas
- Java 21

- Spring Boot 3.5.0

- Maven

- PostgreSQL (persistência)

- H2 (testes)

- Spring Data JPA

- Spring Validation

- Springdoc OpenAPI (Swagger)

- MapStruct (mapeamento automático entre DTOs e entidades)

- Lombok (redução de boilerplate)

- JUnit 5 + MockMvc (testes)

- Docker + Docker Compose (ambientes isolados)

## Desafios e Soluções
- Conflito de dependências entre Springdoc e Spring Boot 3.5.0:
A versão inicial do Springdoc causava NoSuchMethodError. O problema foi solucionado atualizando para a versão mais recente do springdoc-openapi-starter-webmvc-ui, compatível com Spring Boot 3.5.0.

- Gerenciamento de estados dos dispositivos:
A lógica de negócio foi desenhada para impedir alterações e exclusões de dispositivos em uso, garantindo consistência de dados. Essa regra foi aplicada tanto no serviço quanto validada via testes.

- Build em ambiente com acesso limitado à internet:
Para contornar possíveis falhas na obtenção de dependências via Maven, foi implementado um Dockerfile multi-stage. A imagem maven é usada apenas no momento do build e descartada na imagem final, otimizando o tamanho e isolamento.

- Ambiente de execução desacoplado do host:
Com Docker Compose, os serviços de aplicação e banco foram isolados, garantindo uma configuração reproduzível.

- Ambiente separado para testes:
Durante a implementação dos testes de integração, foi identificado que a aplicação tentava se conectar ao banco de dados PostgreSQL configurado para o ambiente de produção (via Docker). Isso causava falhas nos testes automatizados
quando o container do banco não estava em execução. Para contornar esse problema, foi configurado um banco de dados em memória usando H2 exclusivamente para o perfil de testes. Com isso, os testes passaram a rodar de forma isolada,
rápida e sem dependência de infraestrutura externa, garantindo maior confiabilidade e performance no ciclo de desenvolvimento.

## Organização do Código
  ```bash
  src/main/java/com/devices/api
  ├── controller     → Endpoints REST
  ├── dto            → Objetos de transferência de dados
  ├── entity         → Entidades JPA
  ├── mapper         → Classes MapStruct
  ├── repository     → Interfaces JPA
  ├── service        → Regras de negócio
  └── exception      → Tratamento centralizado de erros
  src/test/          → Testes de integração com cobertura dos principais fluxos
  ```

## 🚀 Como executar

1. Clone o projeto:
   ```bash
   git clone https://github.com/seuusuario/devices-api.git
   cd devices-api
2. Inicie os serviços com Docker (altere informações de banco no docker-compose.yml se achar necessário):
   ```bash
   docker compose up --build
3. Acesse a documentação da API (Swagger) em: http://localhost:8080/swagger-ui.html

## Considerações e melhorias
- Alguns princípios SOLID foram aplicados, como SRP e DIP (parcialmente). O SOLID não foi totalmente aplicado por conta de se tratar de uma aplicação relativamente pequena, com domínio simples e pouco sujeito à mudanças e/ou múltiplas interpretações,
aplicar todos os princípios SOLID pode ser um overhead desnecessário nesse projeto. O objetivo aqui foi manter o código simples, com implementações diretas e claras e fáceis de manter. Caso o projeto se estenda, SOLID será bem-vindo.
- Testes unitários não foram incluídos neste projeto porque o foco foi validar a integração entre os componentes da API e o banco de dados, utilizando testes de integração com MockMvc e banco em memória. Como a lógica de negócio é simples e centralizada,
os testes de integração já cobrem de forma eficaz os principais fluxos da aplicação. Essa abordagem se mostrou suficiente para o escopo atual do projeto. Em projetos maiores ou com regras mais complexas, a inclusão de testes unitários seria essencial.

## Observações
- A branch master serve como referência técnica do projeto, contendo a versão com cobertura de testes, configurações avançadas e documentação detalhada. Para uma visão geral das funcionalidades e tecnologias, acesse a branch main.
