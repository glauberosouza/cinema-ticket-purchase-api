# Api para compra de ingresso de cinema

A API para compra de Ingressos de Cinema é um projeto de estudo onde está sendo criado um sistema para gerenciar a venda de ingressos de cinema. A aplicação permite aos usuários criar, comprar e gerenciar ingressos de cinema, oferecendo a opção de seleção de sala e cadeira. A entidade Ticket (Ingresso), contém informações sobre horários, preços e referências para compra, sala e cadeira. Foi utilizado o relacionamento @ManyToOne e @OneToMany entre as entidades.

## Principais tecnologias
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **OpenAPI (Swagger)**
- **Postgre**
-  **Docker**

  
## TDD
Aplicação foi construída seguindo a metodologia de Desenvolvimento Orientado por Testes (TDD). Isso implica a criação de testes de unidade e testes de integração antes mesmo de escrever o código. Inicialmente, os testes são criados para falhar, identificando as funcionalidades ausentes ou com falhas. Posteriormente, o código é desenvolvido para atender a esses testes, garantindo o funcionamento correto. Após a implementação, é realizada uma etapa de refatoração, mantendo o ciclo contínuo de aprimoramento e qualidade do código da aplicação. Esse processo pode parecer mais trabalhoso, no entanto, à medida que os testes passam com sucesso, você ganha mais confiança na criação da API, pois está garantindo maior segurança para a aplicação.

## Projeto em andamento sujeito a alterações no readme em breve.

 ```mermaid
classDiagram
  class Purchase {
    price: BigDecimal
    quantity: Integer
    tickets: Ticket[]
  }

  class Ticket {
    number: String
    day: Date
    session: Time
    price: BigDecimal
    createAt: Date
    chair: Chair
    room: Room
    purchase: Purchase
  }

  class Room {
    movie_name: String
    name: String
    number: Integer
    chairs: Chair[]
  }

  class Chair {
    empty: Boolean
    line: String
    number: Integer
    room: Room
  }
  Purchase "1" -- "N" Ticket
  Ticket "1" -- "1" Room
  Ticket "1" -- "1" Chair
  Chair "N" -- "1" Room

```
