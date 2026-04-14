# AV1 - Desenvolvimento Web III Fatec SJC

**Requisitos: Java 17.** 

## Como rodar

```bash
> ./mvnw clean install
> ./mvnw spring-boot:run
```

Estará disponível em `http://localhost:8080/`.

Os dados serão salvos no banco em memória H2.

## Endpoints

A aplicação possui 4 entidades: Cliente, Documento, Endereço e Telefone.

Cada entidade possui os seguintes endpoints para as ações CRUD, seguindo os níveis de maturidade RMM (Richardson Maturity Model), principalmente na aplicação de HATEOAS.

Exemplo para as rotas do Cliente:

- `POST /clientes`: Cria a entidade
- `GET /clientes/{id}`: Busca entidade do id passado
- `GET /clientes`: Lista todas as entidades criadas
- `PUT /clientes/{id}`: Atualiza a entidade do id passado 
- `DELETE /clientes/{id}`: Remove a entidade do id passado (Apenas para a entidade Cliente)

## Exemplos de Requisições

`POST /clientes:`

```json
{
  "nome": "Gustavo Ribeiro da Rosa",
  "nomeSocial": "Gustavo",
  "dataNascimento": "1990-05-15",
  "documentos": [
    {
      "tipo": "RG",
      "numero": "0000000000"
    }
  ],
  "endereco": {
    "estado": "SP",
    "cidade": "São José dos Campos",
    "bairro": "Eugênio de Melo",
    "rua": "Avenida Cesare Monsueto Giulio Lattes",
    "numero": "100",
    "codigoPostal": "12247-014",
    "informacoesAdicionais": ""
  },
  "telefones": [
    {
      "ddd": "11",
      "numero": "91234-5678"
    },
    {
      "ddd": "12",
      "numero": "99876-5432"
    }
  ]
}
```

`PUT /telefones/1`

```json
{
  "ddd": "88",
  "numero": "456789123"
}
```


`PUT /enderecos/1`

```json
{
    "estado": "SP",
    "cidade": "São José dos Campos",
    "bairro": "Santana",
    "rua": "Av. Olivo Gomes",
    "numero": "100",
    "codigoPostal": "12211-420",
    "informacoesAdicionais": "Parque Roberto Burle Marx - Parque da Cidade"
}
```
