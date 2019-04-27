# Get Marks

Student will get their list of mark report. Student has to generate the token and the same has to be passed in the header.

**URL** : `/v1/reports/get/marks`

**Method** : `GET`

**Auth required** : NO

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
```json
[
    {
        "id": "5cc0c8814a18bf5e585aa1f0",
        "rollNo": "S001",
        "status": "pass",
        "standard": "X",
        "examType": "quarterly",
        "percentage": 69.58,
        "total": 417.5,
        "timestamp": 1556137083131,
        "marks": {
            "chemistry": 85.5,
            "computer": 60,
            "maths": 60,
            "physics": 80,
            "english": 62,
            "kannada": 70
        }
    },
    {
        "id": "5cc0c8ae4a18bf5e585aa1f1",
        "rollNo": "S001",
        "status": "pass",
        "standard": "X",
        "examType": "halfyearly",
        "percentage": 77.92,
        "total": 467.5,
        "timestamp": 1556137093131,
        "marks": {
            "chemistry": 75.5,
            "computer": 80,
            "maths": 80,
            "physics": 70,
            "english": 82,
            "kannada": 80
        }
    },
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
```

## Error Response

**Condition** : Incase of token expired or wrong token entered.

**Code** : `401 UN AUTHORIZED`

**Content** :

```Data
Invalid Token or Token Expired
```
