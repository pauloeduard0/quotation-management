# Quotation Management REST Application

To set up and run the Quotation Management REST application, please ensure you have the following prerequisites:

- Java 17
- Spring Boot
- Maven
- Insomnia or Postman
- Docker on Ubuntu
- Git
- IntelliJ IDEA

 ---

# Initialization

*Clone this repository.*

*Follow the step below to run the project using IntelliJ:*

- Build a Maven project and generate the artifacts in the terminal:

mvn clean install -DskipTests

---

*Run the following commands on Docker:*

- Build the Quotation Management image:

docker build -t quotation-management .

- Start docker compose:

docker compose up

---

# How to Use the API

To register a new stock in the Stock Manager, use the following endpoint:

- POST  http://localhost:8080/stock

Request body:

```
{
    "id": "maglu3", 
    "description": "Magazine Luiza" 
}

```

To retrieve all stocks from the Stock Manager, use the following endpoint:

- GET  http://localhost:8080/stock

Response body:

```
[
    {
        "id": "aapl34",
        "description": "Apple Inc."
    },
    {
        "id": "petr4",
        "description": "Petroleo Brasileiro SA Petrobras Preference Shares"
    },
    {
        "id": "maglu3", 
        "description": "Magazine Luiza" 
    }
]
```

To register a new stock in the Quotation Management, use the following endpoint:

- POST  http://localhost:8081/quote

Request body:

```
{ 
    "stockId": "petr4", 
    "quotes": 
    { 
        "2020-01-01": "10", 
        "2020-01-02": "11", 
        "2020-01-03": "14" 
    } 
}

```

To retrieve all stocks from the Quotation Management, use the following endpoint:

- GET  http://localhost:8081/quote

Response body:

```
{
    "content": [
        {
            "id": "3441bdf3-780d-4984-9da1-9dad5382a4c7",
            "stockId": "petr4",
            "quotes": {
                "2023-01-03": 14.00,
                "2023-01-02": 11.00,
                "2023-01-01": 10.00
            }
            
            "id": "3f99b8fe-55c4-4c49-9552-f60ad204996b",
            "stockId": "apl34",
            "quotes": {
                "2023-01-03": 41.00,
                "2023-01-02": 42.00,
                "2023-01-01": 43.00
            }
        }
    ]
}
```

To retrieve a specific stock by stockId from the Quotation Management, use the following endpoint:

- GET  http://localhost:8081/quote/petr4

Response body:

```
{
    "content": [
        {
            "id": "3441bdf3-780d-4984-9da1-9dad5382a4c7",
            "stockId": "petr4",
            "quotes": {
                "2020-01-03": 14.00,
                "2020-01-02": 11.00,
                "2020-01-01": 10.00
            }
        },
    ]
}
```
