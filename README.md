# Spring Security JWT Integration
This project is a library that allow connect a Spring application using Spring Security and JWT easily.

## Maven Dependency

```
<dependency>
    <groupId>com.github.colopezfuentes</groupId>
    <artifactId>spring-security-jwt-integration</artifactId>
    <version>0.0.1-RELEASE</version>
</dependency>
```

## How to Use it

- Include this dependency in your project.
- Setup the properties
- To generate a new token do a **POST** request a "http://localhost:MY_PORT/MY-CONTEXT-PATH/auth"
with 
```
 {
    "user":"myUser",
    "password":"myPassword"
 }

```
- To request to any other secure endpoint include a header **"Authorization"** with the token.

## Suggestions

  - Re implement a bean for DefaultPasswordEncoder
  - Re implement a bean for DefaultUserDetailService
  - Re implement a bean for DefaultWebSecurityConfig (optional)
  
 These implementations are provided with a not secure default logic 

## Properties

```
security.jwt.secret-key=
security.jwt.expiration-date=  #In seconds
security.jwt.default-password=
security.jwt.default-user=
```

## Authors
[@colopezfuentes](https://github.com/colopezfuentes)
[@diegosep](https://github.com/diegosep)


