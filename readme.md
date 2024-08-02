## RewardSystem

This is a springboot based service which contains functionality of creating a purchase and for every transaction calculate and award points to customer.

### Technology used
+ Java 17
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
#### Show Points
+ URI: "api/reward/customer/[customerId]"
+ Method: GET
+ Response:
```json
{
  "customer": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "555-1234"
  },
  "totalPoints": 330,
  "rewards": [
    {
      "transactionId": "0c3f963f-1169-4980-9034-fdca7de73ee0",
      "transactionAmount": 150.00,
      "points": 150,
      "awardedDate": "2024-08-01T16:31:35.573555"
    },
    {
      "transactionId": "a6464c93-d665-4701-831b-d86bf57c1849",
      "transactionAmount": 120.00,
      "points": 90,
      "awardedDate": "2024-08-01T16:35:22.170311"
    },
    {
      "transactionId": "c66a127d-3e13-4b67-8574-b166f1aa327c",
      "transactionAmount": 120.50,
      "points": 90,
      "awardedDate": "2024-08-01T20:20:14.986162"
    },
    {
      "transactionId": "592d1ba4-ccde-4533-99fc-f86b466fbe0c",
      "transactionAmount": 12.00,
      "points": 0,
      "awardedDate": "2024-08-02T13:00:46.199788"
    }
  ]
}
```

