## Project name
### **Cinema**

## Description
The small online cinema with one hall. 
It has a schedule for showing movies for all 7 days of the week from 9:00 to 22:00 (the beginning of the last show).
An unregistered user can see: a schedule, free seats in the hall, has the opportunity to register.
A registered user should be able to buy a ticket to the selected place. 
The administrator can: add a new film to the schedule, cancel the film, view the attendance of the audience. 

## List of Contents
* [Project name](#project-name)
* [Description](#description)
* [Getting Started](#getting-started)
  * [Requirements](#requirements)
  * [Installation and running](#installation-and-running)
* [Running the tests](#running-the-tests)  
* [Authors](#authors)

## Getting Started

### Requirements
To run the project you need installed: 
  * Java 8 or higher version 
  * Apache Tomcat 8 or higher version
  * Apache Maven 3.5.2
  * MySQL 6.0.6
  
### Installation and running
To install and run the project on localhost:
 * Clone or download the project [cinema](https://github.com/grubjack/cinema) from the GitHub 
 * Create database `cinema` on your MySQL server. After creating the database, edit file `"/src/main/webapp/META-INF/context.xml"`. 
 Specify value of key `username` and `password`. 
 Firstly execute SQL script `"initDB.sql"` and then script `"populateDB.sql"` from the directory `"src/resources/db"` of the project.                                                                                         
 * Compile project to package `"cinema.war"`. To do it just execute command `mvn clean package -DskipTests` 
 (build skipping tests, or command `mvn clean package` if you want to run unit-tests as well)  from your prompt in the project root directory.
 Then put file `"cinema.war"` to the directory `"webapps"` of your Tomcat root folder  
 * Run Tomcat server using script `"startup.sh"` for Mac/Linux/Unix or  `"startup.bat"` for Windows from `"bin"` directory of your Tomcat installation folder. 
 When server starts, the site will be available by URL: 
 [http://localhost:8080/cinema](http://localhost:8080/cinema)
 * To shutdown Tomcat just run script `"shutdown.sh"` for Mac/Linux/Unix or file `"shutdown.bat"` for Windows from `"bin"` directory of your Tomcat root folder.
 
## Running the tests
Project has junit integration tests using in-memory HSQLDB.
To build project with running the unit-test execute command `mvn clean package` from your prompt in the project root directory.

## Authors
Urban Aleksandr (e-mail: [grubjack@gmail.com](mailto:grubjack@gmail.com))


