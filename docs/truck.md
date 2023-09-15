# Truck

### Unload bikes

`POST` http://localhost:8080/api/trucks/{truck-id}:unload

Cet appel permet de sortir des vélos d'un camion.

#### Entrée

##### _body_

```java

{

    "unloadBikeDataList" : [

        {

            "toStationCode": string,

            "bikesLocations": [string]

        }

    ],

}

```

##### _header_

```java

{

	"user-idul": string, (idul)

	"Authorization": Bearer token

}

```

#### Sortie

##### Code de status: 200 Ok

```json

{

  "message": "The bikes were successfully unloaded."

}

```

#### Sorties d'erreur

##### Code de status: 404 Not Found

###### Lorsque le vélo n'existe pas

```json

{

  "error": {

    "code": "404",

    "message": "Bike '1' not found."

  }

}

```

##### Code de status: 404 Not Found

###### Lorsque le camion n'existe pas

```json

{

  "error": {

    "code": "404",

    "message": "Truck Not found"

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

### Load bikes

`POST` http://localhost:8080/api/trucks/{truck-id}:load

Cet appel permet de mettre des vélos dans un camion.

#### Entrée (_body_)

```java

{

    "fromStationCode": string,

    "bikesLocations": [string]

}

```

#### Sortie

##### Code de status: 200 Ok

```java

{

    "message": "The bikes were successfully loaded."

}

```

#### Sorties d'erreur

##### Code de status: 404 Not Found

###### Lorsque le vélo n'existe pas

```json
{
  "error": {
    "code": "404",
    "message": "Bike '1' not found."
  }
}

```

##### Code de status: 404 Not Found

###### Lorsque le camion n'existe pas

```json
{
  "error": {
    "code": "404",
    "message": "Truck Not found"
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
