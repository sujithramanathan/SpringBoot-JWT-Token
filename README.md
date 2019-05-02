# Marksheet
This is an example of SpringBoot + JWT Token ( Authentication, Authorization ) + Morphia + Integration Test cases.


## Open Endpoints

Open endpoints require no Authentication.

* [Token](Token.md) : `POST /v1/auth/token`
* [Get Marks](GetMarks.md) : `GET /v1/reports/get/marks`

## Endpoints that require Authentication

Secured endpoints require a valid Token to be included in the header of the
request. A Token can be acquired from the Login view above and the user should have appropriate roles to access these features.
* [Academic Report](AcademicReport.md) : `GET /v1/academic/reports/year/{academicYear}/standard/{standard}`
* [Academic Subject Topper](AcademicSubjectTopper.md) : `GET /v1/academic/reports/year/{academicYear}/standard/{standard}`
* [Post Marks](PostMarks.md) : `POST /v1/reports/post/marks`
* [Sign Up](SignUp.md) : `POST /v1/auth/signup/{userName}`
