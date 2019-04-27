# Login

Used to collect a Token for a registered User.

**URL** : `/v1/auth/token`

**Method** : `POST`

**Auth required** : NO

**Headers**

```json
{
    "userId": "[valid user id]",
    "password": "[password in plain text]"
}
```

## Success Response

**Code** : `201 OK`

**Content example**

```data
You will get token in a plain text which is valid for 10 mins.
```

## Error Response

**Condition** : If 'userId' and 'password' combination is wrong.

**Code** : `400 BAD REQUEST`

**Content** :

```Data
Invalid username or password
```
