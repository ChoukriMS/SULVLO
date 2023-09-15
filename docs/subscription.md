# Subscription

### Get all subscriptions

`GET` http://localhost:8080/api/subscriptions

Cet appel permet d'aller chercher toutes les subscriptions.

##### _header_

```java
{
	"Authorization": Bearer token
}
```

#### Sortie

##### Code de status: 200 OK

```java
[
  {
    "type": string,
    "price": int,
    "description": string,
    "duration": int
  },
  ...
]

```

#### Sortie d'erreur

##### Code de status: 401 Unauthorized

###### Lorsque les données utilisateur n'existe pas

```json
{
  "error": {
    "code": "401",
    "message": "HTTP 401 Unauthorized"
  }
}
```

### Add a subscription

`POST` http://localhost:8080/api/subscriptions

Cet appel permet d'ajouter une subscription'.

##### _header_

```java
{
	"Authorization": Bearer token
}
```

#### Entrée (_body_)

```java
{
  "subscriptionType": "Base" | "Premium" string,
  "idul": string,
  "creditCardNumber": string, 
  "expirationMonth": number, 
  "expirationYear": number, 
  "ccv":  number,
  "semester": string,
  "automaticPaymentEndMonth": boolean,
  "automaticPaymentAfterTravel": boolean,
  "payWithSchoolFees": boolean
}
```

#### Sortie

##### Code de status: 200 OK

```json

{
  "message": "You have been successfully subscribed"
}

```

#### Sortie d'erreur

##### Code de status: 401 Unauthorized

###### Lorsque les données utilisateur n'existe pas

```json
{
  "error": {
    "code": "401",
    "message": "HTTP 401 Unauthorized"
  }
}
```

##### Code de status: 400 Bad Request

###### Lorsqu'une valeur n'est pas du bon format.

```json

{
  "error": {
    "code": "400",
    "message": "One or more of your subscription parameter is wrong, please try again!"
  }
}

```
