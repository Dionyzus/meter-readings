# meter-readings
## Requirements

For building and running the application you need:

- [MySQL 8](https://www.mysql.com/downloads/)
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Clone the repository
git clone https://github.com/Dionyzus/meter-readings.git

## MySQL
Make sure that you have MySQL installed, run MySQL and create electric_device database. MySQL shell (https://www.inmotionhosting.com/support/server/databases/create-a-mysql-database/) or MySQL workbench and create new schema there. Make sure that 3306 port is not in use by some other process already.

**Make sure that you are connected to mysql instance via 3306 port locally.
**If running with docker no just make sure mysql shell is running.

## Spring boot project via IDE or maven plugin
Unzip the project and save it at desired location.

There are several ways to run a Spring Boot application on your local machine. 
Open IDE and import/open project (from location where you've unzipped it).

**When project is added automatic build should start. If not, make sure to do maven update and to build project!!

Execute the `main` method in the `com.odak.meterreading.MeterreadingApplication` class from your IDE to run it. (Make sure MySQL is running).

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
To test REST endpoints, either use client like postman (https://www.postman.com) 
or visit swagger ui (http://localhost:8080/meter-reading.html) via browser.
Notice, can't provide query parameteres when required is set to false. Use postman to test them.

## Docker
This should create electric_device database, but if any container does not run, make sure you have electric_device database available (Can through MySQL workbench).
**Set of docker commands to run in terminal. (Make sure docker is running).

```shell
docker pull iodak/meter-reading:v3

docker run -d -p 3306:3306 --name localhost -e MYSQL_ROOT_PASSWORD=iodak -e MYSQL_DATABASE=electric_device -d mysql

docker pull flyway/flyway
docker run -d --link localhost flyway/flyway -url=jdbc:mysql://localhost:3306/electric_device -user=root -password=iodak info

docker run -d --link localhost -p 8080:8080 --name meter-reading --link localhost iodak/meter-reading:v3
```

After executing set of commands make sure images are available and containers are running without errors.

## Test REST API
Either use swagger link (http://localhost:8080/meter-reading.html) to see available endpoints

If using postman and for query parameters testing. Here is set of endpoints and rest calls examples below:

## Meter readings
* View readings for month in year. Query params must be listed without spaces, year, month value do not matter.
  * (http://localhost:8080/api/v1/meter-readings?type=monthly&value=7,2021) - get mapping

* View monthly reading for a provided year.
  * (http://localhost:8080/api/v1/meter-readings?type=yearly&value=2021) - get mapping

* Aggregated reading for a provided year.
  * (http://localhost:8080/api/v1/meter-readings?type=aggr&value=2021) - get mapping

* Sort, limit, order example. sortBy field can't be reading_time/reading_value it has to be readingTime/readingValue
  * (http://localhost:8080/api/v1/meter-readings?sortBy=readingValue&sortDir=desc&limit=2) - get mapping

* Get/Update/Delete by id
  * (http://localhost:8080/api/v1/meter-readings/{id}) - make sure correct mapping is used.

Update body example:
{
  "year": "2021",
  "month": "11",
  "reading_value": 5
}

Make sure value for month does not exist already, otherwise request returns error

* Post mapping (http://localhost:8080/api/v1/meter-readings)

 {
  "device_id": 1,
  "year": "2021",
  "month": "05",
  "reading_value": 50
  }

Month if single digit must be provided with zero prefix: 05, not 5.

## Client
* Add new client entry: (http://localhost:8080/api/v1/clients) - post mapping
* Example body (device must not be in use, and address must be unique, returns error if criteria is not matched)
{
  "id": 0,
  "full_name": "New Client",
  "address": {
    "street_name": "New Street",
    "city": "City",
    "postal_code": "10000"
  },
  "device_id": 2
}

* Get mapping - (http://localhost:8080/api/v1/clients) returns clients.

## Device
* Get all devices: (http://localhost:8080/api/v1/devices)
* Get device by id: (http://localhost:8080/api/v1/devices/{id})
* Get meter readings for device: (http://localhost:8080/api/v1/devices/{id}/meter-readings)
