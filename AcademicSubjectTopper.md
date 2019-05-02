# Academic Subject Topper Report

This report will take year, subject and standard as a input in URL path and then provide report.

**URL** : `/v1/academic/reports/topper/year/{academicYear}/subject/{subject}/standard/{standard}`

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
        "id": "5cc0c9f94a18bf5e585aa1f5",
        "rollNo": "S002",
        "status": "pass",
        "standard": "X",
        "examType": "annual",
        "percentage": 87.92,
        "total": 527.5,
        "timestamp": 1557134033131,
        "marks": {
            "chemistry": 82.5,
            "computer": 92,
            "maths": 94,
            "physics": 80,
            "english": 89,
            "kannada": 90
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
