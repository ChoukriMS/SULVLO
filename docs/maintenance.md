# Maintenance

### Start maintenance of a station

`POST` http://localhost:8080/api/stations/under-maintenance/{station-code}:start-maintenance

Cet appel permet de commencer la mainteance d'une station.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
  "user-idul": string Format: idul
}
```

#### Sortie

##### Code de status: 200 OK

```json
{
  "message": "The station is now under maintenance."
}
```

#### Sortie d'erreur

##### Code de status: 400 Bad Request

###### Lorsque la station entree n'existe pas

```json
{
  "error":
  {
    "code": "400",
    "message": "Invalid station code."
  }
}
```

##### Code de status: 403 Forbidden

###### Lorsque les information d'authenfication ou le role ne sont pas adequats

```json
{
  "error":
  {
    "code": "403",
    "message": "User not authorized."
  }
}
```

### End maintenance of a station

`POST` http://localhost:8080/api/stations/under-maintenance/{station-code}:end-maintenance

Cet appel permet de mettre fin a la maintenance d'une station.

##### _header_

```java
{
	"Authorization": Bearer token,
  "user-idul": string Format: idul
}
```


#### Sortie

##### Code de status: 200 OK

#### Sortie d'erreur

##### Code de status: 400 Bad Request

###### Lorsque la station entree n'existe pas

```json
{
  "error":
  {
    "code": "400",
    "message": "Invalid station code."
  }
}
```

##### Code de status: 403 Forbidden

###### Lorsque les information d'authenfication ou le role ne sont pas adequats

```json
{
  "error":
  {
    "code": "403",
    "message": "User not authorized."
  }
}
```


### Request maintenance

`POST` http://localhost:8080/api/users/request-maintenance

Cet appel permet la demande de maintenance d'une station.


#### Entrée (_body_)

```java
{
    "stationCode": string,
    "email": string Format: email
}
```

#### Sortie

##### Code de status: 200 OK

#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque la station entree n'existe pas

```json

{
  "error": {
    "code": "404",
    "message": "Station {station} not found."
  }
}

```

##### Code de status: 401 Unauthorized

###### Lorsque le mail entre n'est pas associe a un compte

```json

{
  "error": {
    "code": "401",
    "message": "HTTP 401 Unauthorized"
  }
}

```


