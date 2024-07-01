[RabbitMQ]: https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white
[Spring]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Swagger]: https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white
[Docker]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Git]: https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white
[GraphQL]: https://img.shields.io/badge/GraphQL-e10098?style=for-the-badge&logo=graphql
[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Spring_Badge]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Postgres]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[Hibernate]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Postman]: https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white
[AWS]: https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white

<h1 align="center" style="font-weight: bold;">Mimo's Pet 💻</h1>

![Java][Java]
![Spring_Badge][Spring]
![Hibernate][Hibernate]
![Postgres][Postgres]
![RabbitMQ][RabbitMQ]
![Docker][Docker]
![Git][Git]
![Swagger][Swagger]

<p align="center">
 <a href="#getting-started">Começando</a> • 
  <a href="#api-endpoints">Endpoints</a> •
 <a href="#microservices">Microservices</a> •
 <a href="#comunicacao">Comunicação</a> •
 <a href="#tecnologias">Tecnologias</a> 
 
 
</p>

<p align="center">
  <b>O projeto é um sistema de gestão de um PetShop, desenvolvido com arquitetura de microservices para garantir modularidade, escalabilidade e fácil manutenção. Cada microservice é responsável por uma funcionalidade específica, como gestão de clientes, produtos, adoções, vendas e animais. Utiliza Spring Boot para construir os microservices e RabbitMQ para comunicação assíncrona. O registro de serviços é gerenciado pelo Eureka Server.</b>
</p>

<h2 id="getting-started">🚀 Começando </h2>

### Pré-requisitos

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [RabbitMQ](https://www.rabbitmq.com/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Git](https://git-scm.com/)

### Clonar

Clone o repositório do projeto:

```bash
git clone git@github.com:HebertFSoares/Mimos-Pet.git
cd Mimos-Pet
mvn clean install
docker-compose up
```

<h2 id="api-endpoints">📍 Endpoints</h2>
Lista das principais rotas da API:

| Rota                              | Descrição                                         |
|-----------------------------------|---------------------------------------------------|
| <kbd>GET /clients</kbd>           | Retorna informações dos clientes.                 |
| <kbd>POST /clients/register</kbd> | Registra um novo cliente.                         |
| <kbd>GET /products</kbd>          | Retorna a lista de produtos.                      |
| <kbd>POST /products</kbd>         | Adiciona um novo produto.                         |
| <kbd>GET /adoptions</kbd>         | Retorna informações sobre adoções.                |
| <kbd>POST /adoptions</kbd>        | Registra uma nova adoção.                         |
| <kbd>GET /sales</kbd>             | Retorna o histórico de vendas.                    |
| <kbd>POST /sales</kbd>            | Registra uma nova venda.                          |
| <kbd>GET /animals</kbd>           | Retorna a lista de animais.                       |
| <kbd>POST /animals</kbd>          | Adiciona um novo animal.                          |

<h2 id="microservices">🔧 Microservices</h2>

### ms_client

- **Descrição:** Gerencia as informações dos clientes, incluindo cadastro, autenticação e atualização de dados.
- **Funcionalidades:** Cadastro de clientes, login, atualização de informações.
- **Tecnologias:** Spring Boot, Spring Security, JWT, RabbitMQ.

### ms_product

- **Descrição:** Gerencia os produtos disponíveis no PetShop, incluindo cadastro, atualização e consulta de produtos.
- **Funcionalidades:** Cadastro de produtos, atualização de informações de produtos, consulta de produtos.
- **Tecnologias:** Spring Boot, Spring Security, JWT, RabbitMQ.

### ms_adoption

- **Descrição:** Gerencia os processos de adoção de animais, incluindo cadastro de interessados e finalização de adoções.
- **Funcionalidades:** Cadastro de interessados, processos de adoção, atualização de status de adoção.
- **Tecnologias:** Spring Boot, Spring Security, JWT, RabbitMQ.

### ms_sales

- **Descrição:** Gerencia as vendas de produtos e serviços, incluindo registro de vendas e processamento de pagamentos.
- **Funcionalidades:** Registro de vendas, processamento de pagamentos, consulta de histórico de vendas.
- **Tecnologias:** Spring Boot, Spring Security, JWT, RabbitMQ.

### ms_animals

- **Descrição:** Gerencia as informações dos animais disponíveis para adoção ou venda, incluindo cadastro e atualização de dados.
- **Funcionalidades:** Cadastro de animais, atualização de informações, consulta de animais disponíveis.
- **Tecnologias:** Spring Boot, Spring Security, JWT, RabbitMQ.

### ms_gateway

- **Descrição:** Atua como um gateway para as requisições, roteando-as para os microservices apropriados e gerenciando a segurança.
- **Funcionalidades:** Roteamento de requisições, segurança centralizada.
- **Tecnologias:** Spring Cloud Gateway.

### ms_eureka

- **Descrição:** Serve como um registro de serviços, permitindo que os microservices descubram uns aos outros e se comuniquem.
- **Funcionalidades:** Registro de microservices, descoberta de serviços.
- **Tecnologias:** Eureka Server, Spring Security.

<h2 id="comunicacao">🔗 Comunicação entre Microservices</h2>

A comunicação entre os microservices é feita de forma assíncrona utilizando RabbitMQ. Cada microservice publica eventos em um exchange específico e consome mensagens de uma fila específica. Essa abordagem garante desacoplamento e escalabilidade do sistema.

<h2 id="tecnologias">🛠️ Tecnologias Utilizadas</h2>

- **Spring Boot:** Framework principal para a construção dos microservices.
- **Spring Security:** Para autenticação e autorização.
- **JWT:** Para tokens de autenticação.
- **RabbitMQ:** Para comunicação assíncrona entre microservices.
- **Eureka Server:** Para registro e descoberta de serviços.
- **Spring Cloud Gateway:** Para gerenciamento de roteamento e segurança das requisições.

