# ArquitecturaG3 

This project has an implementation of a CRUD where you will be able to CREATE, READ, UPDATE and DELETE an USER from a Postgres Database. 
We followed the DDD reference Architecture with an Onion Model using Scala functional Programming principles.

# Getting started 

Before the implementation of our CRUD its necessary to have already Installed SBT, JVM, POSTGRES Just in case follow the next links to know how to Install them:

* **[SCALA SBT](https://www.scala-lang.org/)**
* **[DOCKER DOCUMENTATION](https://docs.docker.com/)**
* **[JVM ORACLE](https://www.java.com/en/download/)**

# System characteristics 🔧

Here you will find the dependencies and libraries we use.
- [characteristics](https://github.com/santiahernandez/ArquitecturaG3/blob/master/build.sbt)

# Deployment 🚀
**In the project resources (ArquitecturaG3/src/main/resources)**
``` 
$ docker-compose up -d
``` 
**In the project root (ArquitecturaG3)**
``` 
$ sbt run
```
**If Google chrome is installed you can display the documentation by running the following the command in the project main package(ArquitecturaG3/src/main)**
```
$ google-chrome Documentation.html
```
Or firefox
```
$ firefox Documentation.html
```
# Content 🗃

* **[User: ](https://github.com/santiahernandez/ArquitecturaG3/blob/master/src/main/scala/co/s4ncampus/fpwithscala/users/domain/User.scala)** Set User atributes
* **[Service: ](https://github.com/santiahernandez/ArquitecturaG3/blob/master/src/main/scala/co/s4ncampus/fpwithscala/users/domain/UserService.scala)** Wrap Data
* **[Interpreter: ](https://github.com/santiahernandez/ArquitecturaG3/blob/master/src/main/scala/co/s4ncampus/fpwithscala/users/domain/UserValidationInterpreter.scala)** Validate user 
* **[Persistence: ](https://github.com/santiahernandez/ArquitecturaG3/blob/master/src/main/scala/co/s4ncampus/fpwithscala/users/infraestructure/repository/DoobieUserRepositoryInterpreter.scala)** In charge of carrying out the direct communication methods in SQL. 
* **[Controller: ](https://github.com/santiahernandez/ArquitecturaG3/blob/master/src/main/scala/co/s4ncampus/fpwithscala/users/controller/UsersController.scala)** Manage the user requests in order to send them to the service layer
 
# Authors :hocho:
* Santiago Hernández Montaño. 
* David Barrera Acosta.
* David Ríos Vélez.
* Sergio Esteban Muñoz Barón.
