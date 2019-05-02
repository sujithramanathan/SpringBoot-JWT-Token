
# Post Marks

Admin user can able to create login for students.

**URL** : `/v1/reports/post/marks`

**Method** : `POST`

**Auth required** : Yes

**Headers**

```json
{
    "userId" : "userId as student roll number",
    "password" : "password as plain text",
    "role" : "ROLE (Incase of empty string, then role will be set as STUDENT)",
    "Authorization": "Token",
}
```

**Request Body**
```json
{
    "rollNo" : "student roll number",
    "standard" : "standard / class",
    "examType" : "Quarterly / HalfYearly / Annual",
    "marks": {"subject":"marks","subject":"marks"}
}


## Success Response

**Code** : `201 Created`

**Content example**

```data
Record Created.
```

## Error Response

**Condition** : Incase of token expired or wrong token entered.

**Code** : `401 UN AUTHORIZED`

**Content** :

```Data
Invalid Token or Token Expired
```
