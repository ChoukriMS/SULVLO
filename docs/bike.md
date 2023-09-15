# Bike

### Get bikes availabilities

ONLY ADMIN

`GET` http://localhost:8080/api/stations/available

Cet appel permet de voir les vélos disponibles.

#### Entrée (_header_)

```java
{
	"Authorization": Bearer token
}
```

#### Sortie

##### Code de status: 200 Ok

```java

{
    "numberOfAvailableBikes": number,
    "numberOfTakenBikes": number
}

```

#### Sorties d'erreur

##### Code de status: 403 Forbidden

###### Lorsque l'utilisateur n'est pas admin

```json

{
  "error": {
    "code": "403",
    "message": "User not authorized."
  }
}

```

##### Code de status: 400 Bad Request

###### Le token utilisateur n'existe pas

```json

{
  "error": {
    "code": "400",
    "message": "Invalid authentification token"
  }
}

```

### Return bike

`POST` http://localhost:8080/api/stations/available/{return-station-code}/bikes/{return-bike-location}:return

Cet appel permet de retourner un vélo dans une station.

#### Entrée (_header_)

```java

{
  "unlock-station-code": string,
  "unlock-bike-location": string,
	"Authorization": Bearer token
}

```

#### Sortie

##### Code de status: 200 Ok

```json

{
  "message": "The bike was successfully returned."
}

```

#### Sorties d'erreur

##### Code de status: 403 Forbidden

###### Lorsque le vélo selectionné n'as pas de batterie

```json

{
  "error": {
    "code": "400",
    "message": "Bike low on energy cannot be unlocked."
  }
}

```

##### Code de status: 400 Bad Request

###### Le token utilisateur n'existe pas

```json

{
  "error": {
    "code": "400",
    "message": "Invalid authentification token"
  }
}

```

##### Code de status: 404 Not Found

###### Le vélo n'a pas se vélo

```json

{
  "error": {
    "code": "404",
    "message": "Bike '3' not found."
  }
}

```

### Unlock bike

`POST` http://localhost:8080/api/stations/available/{station-code}/bikes/{bike-location}:unlock

Cet appel permet de déverrouiller un vélo dans une station.

#### Entrée (_header_)

```java

{
  "user-code": number,
	"Authorization": Bearer token
}

```

#### Sortie

##### Code de status: 200 Ok

```json

{
  "message": "The bike was successfully unlocked."
}

```

#### Sorties d'erreur

##### Code de status: 403 Forbidden

###### Lorsque le vélo selectionné n'as pas de batterie

```json

{
  "error": {
    "code": "400",
    "message": "Bike low on energy cannot be unlocked."
  }
}

```

##### Code de status: 404 Not Found

###### Lorsque la station n'existe pas

```json

{
  "error": {
    "code": "404",
    "message": "Station 'VAN' not found."
  }
}

```

##### Code de status: 404 Not Found

###### Lorsque le vélo n'existe pas dans la station

```json

{
  "error": {
    "code": "404",
    "message": "Bike '7' not found."
  }
}

```

##### Code de status: 400 Bad Request

###### Lorsque le code unique n'est plus valable

```json

{
  "error": {
    "code": "400",
    "message": "The unique token that you provided has expired!"
  }
}

```

##### Code de status: 400 Bad Request

###### Le token utilisateur n'existe pas

```json

{
  "error": {
    "code": "400",
    "message": "Invalid authentification token"
  }
}

```
