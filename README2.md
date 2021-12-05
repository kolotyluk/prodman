# Additional Instructions

Basically this assigment looks like the typical situation where you start working at a new company,
and are asked to start working on a project this is incomplete, so I will work on completing some
issues I think are lacking, starting with documentation.

## Docker

## Postgres

By default, when doing a Gradle `bootRun`, the applications will not find PostgreSQL running,
so the development environment needs to be established.

### Database Admin/Client

Download and install https://www.pgadmin.org

### Docker

Create a stack.yml file with the following contents, but change the password

    # Use postgres/example user/password credentials
    version: '3.1'

    services:

      db:
        image: postgres
        restart: always
        ports:
        - 5432:5432
        environment:
          POSTGRES_PASSWORD: mypassword

Start up the docker container with

    docker-compose -f stack.yml up

check the log for 

    db_1       | 2021-11-29 18:50:59.118 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
    db_1       | 2021-11-29 18:50:59.118 UTC [1] LOG:  listening on IPv6 address "::", port 5432
    db_1       | 2021-11-29 18:50:59.124 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
    db_1       | 2021-11-29 18:50:59.130 UTC [62] LOG:  database system was shut down at 2021-11-29 18:50:58 UTC
    db_1       | 2021-11-29 18:50:59.135 UTC [1] LOG:  database system is ready to accept connections

Check Docker Desktop

Run pgAdmin, and connect to the DBMS to verify that it is running, and create the `prodmandb` database.

# Running the ProdmanApplication

Run the application with `gradle bootRun`

In a web browser, enter localhost:8080 and you should see

    Product Manager

# Testing

    curl -i -d '{"name":"wiget","description":"What you have always wanted","price":"100.0"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/product
    curl -i http://localhost:8080/api/product/1
    curl -i -X DELETE http://localhost:8080/api/product/1
    curl -i http://localhost:8080/api/product/1

## Automated Tests

I would have written more tests, but I had too much trouble fighting with Spring's test framework;
so, dispirited, I gave up.

Normally I like to practice Test Driven Development, and tend to write automated tests as I go along.
I like using JUnit 5 (Jupiter) because I can define unit, integration, and other tests in the same file
and tag them such that we can specify what types of tests to run. For example, unit tests are run by
the Maven `test` goal or Gradle `test` task, while integration tests are run by the Maven `verify` goal
or Gradle `check` task.
 
# Narrative

When starting with the project, many things were incomplete, as it would not just run out of the box,
and there was no documentation on how to get started. Basically, this is typical scenario when on-boarding
with a new project at a new company. I am assuming this was intentional and part of the assignment. 😉



## Java

This project seems to be designed to use Java 13, but I changed it to Java 17, the current LTS
(Long Term Support) release. Also, I wanted to use important features like Java Records.

### Records

I converted ProductDTO to ProductModel

1. I don't like acronyms such as DTO
2. ProductModel is a record not a class
3. It is better to use Java Records than for data transport rather than regular classes
   1. You don't have to use Lombok. Lombok adds complexity to the build, and can be a source
      of troublesome errors.
   2. It is safer to serialize and deserialize because the original Java serialization is defective
      because it uses reflection, which is one reason records were created.
   3. [Why We Hate Java Serialization And What We're Doing About It by Brian Goetz & Stuart Marks](https://www.youtube.com/watch?v=dOgfWXw9VrI)
4. See also the source code comments for ProductDTO

## Gradle

While I have some experience with Gradle, I generally find it more difficult to work with than Maven
because it lacks product maturity. For example, when things go wrong, Gradle has terrible diagnostics,
so it is common to waste a lot of time on wild goose chases because Gradle often tells you the wrong
things.

## Spring

I am not a Spring expert, so it took me a while to get my bearings enough to start changing code.
In general fighting with Spring took up a lot of time. Builds kept breaking, and the diagnostics
are not good. Generally I fixed thing by upgrading to the latest versions.

## PostgreSQL

It took a while to get a local instance of PostgreSQL running under Docker for test purposes.

If I had more time, I would have automated PostgreSQL Docker startup in the test framework. I have
done this before, but it can take a while to get everything right.

### Java Persistence API

This application uses Integers for the persistent Unique Identifier. If I had more time I would
change this to use UUIDs because it makes the data more portable. Also, many DBMSs, like PostgreSQL,
use UUIDs internally, so they are optimized for that.

## JUnit

I prefer JUnit 5 for many reasons, but it took a fair bit of time just to get a single test working.

1. I have never written tests for Spring before, so I had to research that.
2. I had to upgrade Spring from 2.5.2 to 2.6.1
3. I had to upgrade to Jupiter 5.8.2
4. In the end, I just gave up and focused on getting project working and testing with `curl`

