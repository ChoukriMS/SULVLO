# User

### Register

`POST` http://localhost:8080/api/users/register

Cet appel permet de créer un compte.

#### Entrée (_body_)

```java
{
    "name": string,
    "email": string, Format: email
    "idul": string,
    "age": int,
    "password": string,
    "birthDate": string, Format: DD/MM/YYYY
    "gender": "MALE" Format: "MALE" | "FEMALE" | "OTHER"
}
```

#### Sortie

##### Code de status: 201 Created

```json
{
  "message": "User successfully created."
}
```

#### Sortie d'erreur

##### Code de status: 400 Bad Request

###### Lorsqu'une valeur n'est pas du bon format.

```json
{
  "error": {
    "code": "400",
    "message": "you have entered one or more invalid field, please try again!"
  }
}

```

### Login

`POST` http://localhost:8080/api/users/login

Cet appel permet de se connecter à son compte.

#### Entrée (_body_)

```java
{
	"email": string, Format: email
	"password": string
}
```

#### Sortie

##### Code de status: 200 Ok

```java
{
	"token": string,
	"expireIn": string
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

### Activate Account

`POST` http://localhost:8080/api/users/activation

Cet appel permet d'activer le compte avec le code reçu par mail.

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
	"activation-token": number, (code reçu par mail)
}
```

#### Sortie

##### Code de status: 200 Ok

```java
{
    "message": "User is successfully verified."
}
```

#### Sortie d'erreur

##### Code de status: 401 Unauthorized

###### Lorsque le mail ou le code de vérification est mauvais

```json
{
  "error": {
    "code": "401",
    "message": "HTTP 401 Unauthorized"
  }
}
```


### Protected

`GET` http://localhost:8080/api/users/protected

Cet appel permet de vérifier si l'utilisateur à les droits admin.

#### Entrée (_header_)

```java
{
	"Authorization": Bearer token
}
```

#### Sortie

##### Code de status: 202 Accepted

```java
{
    "name": string,
    "idul": string, Format: idul
    "email": string, Format: email
    "roles": string
}
```

#### Sortie d'erreur

##### Code de status: 403 Forbidden

###### Lorsque l'utilisateur n'as pas les droits d'utiliser la route

```json
{
  "error": {
    "code": "403",
    "message": "User not authorized."
  }
}
```

### Register

`POST` http://localhost:8080/api/users/request-maintenance

Cet appel permet de faire une demande de maintenance.

#### Entrée (_body_)

```java
{
    "stationCode": string,
    "email": string, Format: email
}
```

#### Sortie

##### Code de status: 200 Ok

#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque la station n'existe pas.

```json
{
    "error": {
        "code": "404",
        "message": "Station 'd' not found."
    }
}
```