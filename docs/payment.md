# Payment

### Pay extra fees

`POST` http://localhost:8080/api/payment/pay-extra-fees

Cet appel permet de payer les frais.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
}
```

#### Sortie

##### Code de status: 200 OK


#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque le token n'est pas valide

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```
##### Code de status: 409 Conflict

###### L'utilisateur n'as pas de frais a payer.

```json
{
    "error": {
        "code": "409",
        "message": "You already paid what you owed."
    }
}
```

### Pay debt

`POST` http://localhost:8080/api/payment/pay-debt

Cet appel permet de payer les dettes.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
}
```

#### Sortie

##### Code de status: 200 OK


#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque le token n'est pas valide

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```
##### Code de status: 409 Conflict

###### L'utilisateur n'as pas de frais a payer.

```json
{
    "error": {
        "code": "409",
        "message": "You already paid what you owed."
    }
}
```
### Pay subscription

`POST` http://localhost:8080/api/payment/pay-subscription

Cet appel permet de payer l'abonnement.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
}
```

#### Sortie

##### Code de status: 200 OK


#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque le token n'est pas valide

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```
##### Code de status: 409 Conflict

###### L'utilisateur n'as pas de frais a payer.

```json
{
    "error": {
        "code": "409",
        "message": "You already paid what you owed."
    }
}
```

### Configurer paiment apres voyage

`POST` http://localhost:8080/api/payment/configure/after-travel

Cet appel permet de configurer le paiement après les trajets.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
}
```
##### _body_

```java
{
    "automaticPayment": boolean
}
```

#### Sortie

##### Code de status: 200 OK

```java
{
    "message": "Automatic payment after travel updated."
}
```

#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque le token n'est pas valide

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```

### Configurer paiment à la fin du mois

`POST` http://localhost:8080/api/payment/configure/end-month

Cet appel permet de configurer le paiement pour la fin du mois.

#### Entrée

##### _header_

```java
{
	"Authorization": Bearer token,
}
```
##### _body_

```java
{
    "automaticPayment": boolean
}
```

#### Sortie

##### Code de status: 200 OK

```java
{
    "message": "Automatic payment end month updated."
}
```

#### Sortie d'erreur

##### Code de status: 404 Not Found

###### Lorsque le token n'est pas valide

```json
{
    "error": {
        "code": "400",
        "message": "Invalid authentification token"
    }
}
```