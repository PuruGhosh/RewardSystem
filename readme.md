## RewardSystem

This is a springboot based service which contains functionality of creating a purchase and for every transaction calculate and award points to customer.

### Technology used
+ Java 17
+ Gradle 8.7
+ Springboot v 3.3.2
+ Postgresql 16

### Flow Diagram
#### Create Reward Point Flow
![Flow Diagram](docs/POINT_CREATION.png)

#### Get Reward Point Flow
![Flow Diagram](docs/GET_REWARDS_CID.png)

### API Endpoints

#### Purchase
+ URI: "api/txn/createTransaction"
+ Method: POST
+ Function: Process one transaction for a customer
+ Req Body:
```json
{
    "customerId": "123e4567-e89b-12d3-a456-426614174000",
    "amount": 120
}
```
+ Response:
```
Transaction processed and rewards awarded
```

#### Bulk Purchase
+ URI: "api/txn/createBulkTransaction"
+ Method: POST
+ Process multiple transaction for a customer
+ Req Body:
```json
{
  "customerId": "123e4567-e89b-12d3-a456-426614174002",
  "transactions":[
    {
      "amount":130,
      "transactionDate":"2024-04-12"
    },
    {
      "amount":140,
      "transactionDate":"2024-07-12"
    },
    {
      "amount":150,
      "transactionDate":"2024-08-03"
    }
  ]
}
```
+ Response:
```json
[
  {
    "id": "5b75b2b1-cc97-426a-9e32-94f00c5e60ff",
    "customer": {
      "id": "123e4567-e89b-12d3-a456-426614174002",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "phoneNumber": "555-8765"
    },
    "amount": 130,
    "transactionDate": "2024-04-12T10:00:00"
  },
  {
    "id": "de5cb005-d7e4-4caf-a97f-639533061e73",
    "customer": {
      "id": "123e4567-e89b-12d3-a456-426614174002",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "phoneNumber": "555-8765"
    },
    "amount": 140,
    "transactionDate": "2024-07-12T10:00:00"
  },
  {
    "id": "21bb243f-0e94-441c-9b5f-c026426644d5",
    "customer": {
      "id": "123e4567-e89b-12d3-a456-426614174002",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "phoneNumber": "555-8765"
    },
    "amount": 150,
    "transactionDate": "2024-08-03T10:00:00"
  }
]
```

#### Show Points
+ URI: "api/reward/customer/123e4567-e89b-12d3-a456-426614174002?months=10"
+ ** In uri request parameter 'months' is optional. default value is 3 **
+ Method: GET
+ Function: It gives the rewards for a customer for time specified in months(if not specified, 3)
+ Response:
```json
{
  "customer": {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "phoneNumber": "555-8765"
  },
  "totalPoints": 390,
  "rewards": [
    {
      "transactionId": "5b75b2b1-cc97-426a-9e32-94f00c5e60ff",
      "transactionAmount": 130.00,
      "points": 110,
      "awardedDate": "2024-04-12T10:00:00"
    },
    {
      "transactionId": "de5cb005-d7e4-4caf-a97f-639533061e73",
      "transactionAmount": 140.00,
      "points": 130,
      "awardedDate": "2024-07-12T10:00:00"
    },
    {
      "transactionId": "21bb243f-0e94-441c-9b5f-c026426644d5",
      "transactionAmount": 150.00,
      "points": 150,
      "awardedDate": "2024-08-03T10:00:00"
    }
  ]
}
```

### More evidence in 'Evidence' folder
