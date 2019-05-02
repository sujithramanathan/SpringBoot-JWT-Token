# Academic Report

This report will take year and standard as a input in URL path and then provide report for the particular class.

**URL** : `/v1/academic/reports/year/{academicYear}/standard/{standard}`

**Method** : `GET`

**Auth required** : Yes

**Headers**

```json
{
    "Authorization": "Token",
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```data
[
    {
        "id": "5cc0c8d74a18bf5e585aa1f2",
        "rollNo": "S001",
        "status": "pass",
        "standard": "X",
        "examType": "annual",
        "percentage": 90.75,
        "total": 544.5,
        "timestamp": 1556137093131,
        "marks": {
            "chemistry": 93.5,
            "computer": 95,
            "maths": 83,
            "physics": 92,
            "english": 90,
            "kannada": 91
        }
    }
]
```

## Error Response

**Condition** : Incase of token expired or wrong token entered.

**Code** : `401 UN AUTHORIZED`

**Content** :

```Data
Invalid Token or Token Expired
```
