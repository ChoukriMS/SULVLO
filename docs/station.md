# Station

### Get all stations available

`GET` http://localhost:8080/api/stations/available

Cet appel permet d'avoir les stations disponibles.

##### _header_

```java

{
	"Authorization": Bearer token,
}

```

#### Sortie

##### Code de status: 200 OK

```java
[
  {
    "currentDate": date,
    "location": string,
    "name": string,
    "code": string,
    "currentCapacity": int,
    "emptyBikesLocations": [

      int,
      int...

    ],
    "bikes": [
      {
        "location": int,
        "energyLevel": string,
        "status": string
      }
    ]
  }
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

### Get all stations under maintenance 

`GET` http://localhost:8080/api/stations/under-maintenance

Cet appel permet d'avoir les stations en maintenance.

##### _header_

```java
{
	"Authorization": Bearer token,
  "user-idul": string Format: idul
}
```

#### Sortie

##### Code de status: 200 OK

```java
[
  {
    "currentDate": date,
    "location": string,
    "name": string,
    "code": string,
    "currentCapacity": int,
    "emptyBikesLocations": [
      int,
      int...
    ],
    "bikes": [
      {
        "location": int,
        "energyLevel": string,
        "status": string
      }
    ]
  }
]
```

#### Sortie d'erreur

##### Code de status: 403 Forbidden

###### Lorsque les données utilisateur approprié (Technicien) n'existe pas

```json
{
  "error": {
    "code": "403",
    "message": "User not authorized."
  }
}
```

### Unique code

`POST` http://localhost:8080/api/stations/unique-code

Cet appel permet d'obtenir par mail un unique code pour débloquer un vélo.

#### Entrée

##### _body_

```java
{
	"email": string, Format: email
}
```

##### _header_

```java
{
	"Authorization": Bearer token
}
```

#### Sortie

##### Code de status: 200 Ok

```java
{
    "message": "Unique code was successfully sent to your email."
}
```

#### Sortie d'erreur

##### Code de status: 401 Unauthorized

###### Lorsque le mail ou le code de vérification est mauvais ou si l'utilisateur n'as pas d'abonnement.

```json
{
  "error": {
    "code": "401",
    "message": "HTTP 401 Unauthorized"
  }
}
```