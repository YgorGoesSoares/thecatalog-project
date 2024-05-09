# Projeto de Back-end de Catálogo

Este projeto é um back-end de um marketplace que gerencia produtos e suas categorias. Além disso, oferece autenticação para permitir que apenas administradores realizem operações de CRUD em categorias e produtos. Este documento fornece uma visão geral das funcionalidades e dos requisitos de acesso do sistema, além de destacar as tecnologias utilizadas no projeto.

## Funcionalidades

- **Catálogo de Produtos**: Gerencia produtos que estão associados a suas respectivas categorias.
- **Autenticação**: Permite o acesso controlado ao sistema com base em permissões de usuário.
- **Autorização**: Somente usuários com papel de administrador têm permissão para alterar categorias e produtos.

## Requisitos de Acesso

- **Público**:
    - Realizar login (`POST /signin`).
    - Acessar a documentação de API (`/v3/api-docs/**`, `/swagger-ui.html`, `/swagger-ui/**`).
    - Visualizar produtos (`GET /products/**`).
- **Administradores** (`ROLE_ADMIN`):
    - Criar novos usuários (`POST /users`).
    - Gerenciar usuários (`/users/**`).
    - Gerenciar categorias (`/categories/**`).
    - Gerenciar produtos (`/products/**`).

## Tecnologias Utilizadas

- **Spring Boot**: Plataforma para criar aplicações Java baseadas em Spring.
- **Spring Security**: Framework para autenticação e autorização de segurança.
- **Springdoc**: Ferramenta para a geração de documentação de API com Swagger.
- **Spring Data JPA**: Biblioteca para interagir com bancos de dados usando JPA.
- **H2 Database**: Banco de dados em memória para testes.
- **JUnit e Mockito**: Ferramentas para testes em Java.
- **Java JWT**: Biblioteca para trabalhar com tokens JWT.

## Documentação

- A documentação do projeto está disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).


## Como Configurar e Executar o Projeto

Siga as instruções abaixo para configurar e executar o projeto localmente:

1. Clone o repositório do projeto:

    ```shell
    git clone https://github.com/YgorGoesSoares/thecatalog-project
    ```

2. Navegue até o diretório do projeto:

    ```shell
    cd thecatalog-project
    ```

3. Compile e execute o projeto usando Maven:

    ```shell
    mvn spring-boot:run
    ```

4. A aplicação estará em execução em `http://localhost:8080`.
A documentação ficará disponível em `http://localhost:8080/swagger-ui/index.html`.

## Usuários de Teste

O projeto possui dois usuários de teste com diferentes permissões:

- **ROLE_OPERATOR**:
    - E-mail: `alex@gmail.com`
    - Senha: `password`
- **ROLE_ADMIN**:
    - E-mail: `maria@gmail.com`
    - Senha: `password`

## Testes

O projeto possui testes unitários escritos com JUnit e Mockito para garantir a qualidade do código e a integridade das funcionalidades.

## Diagrama Conceitual

Abaixo está o diagrama conceitual do projeto:

![Diagrama Conceitual](https://i.imgur.com/VFW4upc.png)

---

Feito por Ygor Goes | [LinkedIn](https://www.linkedin.com/in/ygor-goes).
