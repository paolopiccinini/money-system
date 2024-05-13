# Money System

Write a standalone microservice that is able to perform the 4 arithmetic operations (sum,
subtraction, multiplication and division) for pre-1970 UK prices.

The service will accept via
HTTP request an operation, and returns a JSON containing the result of the operation.


Under the old money system of UK, before 1970, there were 12 pence in a shilling and 20
shillings, or 240 pence, in a pound. Thus, a price in the Old UK Money system was
expressed in Pounds, Shillings and Pence. For example "12p 6s 10d" is 12 Pounds, 6
Shillings and 10 Pence.


The web service should be able to receive and produce strings in the format "Xp Ys Zd".
Deliverable
Produce a self-contained executable (e.g. jar, war, docker, ...) implementing the pre-1970
prices operations. You can add additional metadata to the source code such as the .git folder.


Send all source code and executables as a compress package via email.
Technologies


You can use any JVM-based language, and you are free to use any framework you like. If
you are in doubt, we would like to receive a Spring Boot dockerized application.

## Installation with Docker

```bash
docker build -t money-system .
docker run -p 8090:8090 money-system
```

## Info

The swagger listens [here](http://localhost:8090/swagger-ui/index.html)

Unlikely I have versioned the api's with content negotiation (Accept header) and swagger is not so smart to understand the right media type to pass in the request (it keeps passing application/json), anyway I have added a postman collection.

you can find request and responses [here](http://localhost:8090/actuator/httpexchanges) I've not logged them (don't know why there is a wired exception when you check that link), the body is not logged there is some work to do but it's possible to do that

I have used Query param for multiply and divide only for different validation, I think is possible to have different validation on the same object I've not digged into it. They must be positive integers but I think there will be no problem with negative numbers

Also the strings in input must be positive integers but with some work we can use negative numbers also for them.

Sorry for the naming I'm really bad at it XD