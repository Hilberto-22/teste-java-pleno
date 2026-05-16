# Teste Tecnico - API de Cupons

Aplicacao Java com Spring Boot para cadastro, consulta e remocao logica de cupons.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Banco H2 em memoria
- Swagger / OpenAPI
- Maven

## Como executar

### Localmente

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicacao sobe por padrao em:

```text
http://localhost:8081
```

### Com Docker

```bash
docker compose up --build
```

## Documentacao e utilitarios

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`
- Console H2: `http://localhost:8081/h2-console`

Configuracao padrao do banco H2:

- JDBC URL: `jdbc:h2:mem:banco_teste`
- Usuario: `sa`
- Senha: vazia

## Endpoints principais

- `POST /coupon` - cria um cupom
- `GET /coupon/{id}` - busca um cupom por ID
- `DELETE /coupon/{id}` - remove logicamente um cupom

## Exemplo de criacao

### Requisicao

```json
{
  "code": "ABC123",
  "description": "Cupom de desconto",
  "discountValue": 10.0,
  "expirationDate": "2026-12-31T23:59:59",
  "published": true
}
```

### Resposta esperada

```json
{
  "id": "uuid-gerado",
  "code": "ABC123",
  "description": "Cupom de desconto",
  "discountValue": 10.0,
  "expirationDate": "2026-12-31T23:59:59",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}
```

## Regras de negocio resumidas

- O codigo do cupom deve ter 6 caracteres alfanumericos.
- O valor de desconto deve ser maior que `0.5`.
- A data de expiracao deve estar no futuro.
- A exclusao e logica, ou seja, o registro nao e apagado fisicamente.

## Testes

Para executar os testes:

```bash
./mvnw test
```
