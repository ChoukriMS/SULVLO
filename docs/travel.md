# Travel

### Get all travels

`GET` http://localhost:8080/api/travel

Cet appel permet de récuperer tous les trajets.
##### _header_

```java
{
	"Authorization": Bearer token,
  "month": String
}
```
#### Sortie

##### Code de status: 200 Ok

```java
{
    [
        {
            "Id": string,
            "user_id": string,
            "stationStartTravel": string,
            "startTravelDate": string,
            "endTravelDate": string,
            "stationEndTravel": string,
            "month": string
        }
    ]
}
```

### Get travel by id

`GET` http://localhost:8080/api/travel/{travel-id}

Cet appel permet de récuperer un trajet par id.

#### Entrée (_header_)

```java
{
	"Authorization": Bearer token,
}
```

#### Sortie

##### Code de status: 200 Ok

```java
{
    "Id": string,
    "user_id": string,
    "stationStartTravel": string,
    "startTravelDate": string,
    "endTravelDate": string,
    "stationEndTravel": string,
    "month": string
}
```

#### Sortie d'erreur

##### Code de status: 400 Bad Request

###### Lorsque le token n'est pas valide.

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```

##### Code de status: 400 Bad Request

###### Lorsque l'id du travel n'existe pas.

```json
{
  "error": {
    "code": "400",
    "message": "There is no travel with that ID, please try again!"
  }
}
```

### Get travel history summary

`GET` http://localhost:8080/api/travel/summary

Cet appel permet de récuperer un résumé de trajet par utilisateur.

#### Entrée (_header_)

```java
{
	"Authorization": Bearer token,
}
```

#### Sortie

##### Code de status: 200 Ok

```java
{
    "totalTravelsTime": string,
    "averageTravelTime": string,
    "numberOfTravels": number,
    "favoriteStation": string
}
```

#### Sortie d'erreur

##### Code de status: 400 Bad Request

###### Lorsque l'id de l'utilisateur n'existe pas

```json
{
  "error": {
    "code": "400",
    "message": "You have entered an invalid user ID!"
  }
}
```