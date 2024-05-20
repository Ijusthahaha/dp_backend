# Student DP Management System

A system to manage student's demerit point.

## Table of Contents

- [Introduction](#introduction)
- [Install](#install)
- [License](#license)

## Introduction
This is Student DP Management System's backend services.

### Technologies Used
- Spring Boot: Framework for building Java applications.

- MySQL: Relational database management system.

- Redis: Key-value store used for caching and session management.

- MyBatis: Persistence framework for mapping SQL statements to Java methods.

- MyBatis-Plus: MyBatis extension library that provides additional features and utilities for simplifying development.

Frontend: https://github.com/Ijusthahaha/dp_management

## Install
This project requires [Java](https://www.java.com) and [Maven](https://maven.apache.org) installed. Make sure you have MySQL and Redis set up as well.
```sh
# clone the project
$ git clone https://github.com/Ijusthahaha/dp_backend.git
```

### Import Table Structures
Before running the application, import the table structures into MySQL by following these steps:
1. Navigate to the `/src/main/resources/sql` directory in the project.
2. Open a terminal or command prompt.
3. Log in to MySQL using the command-line tool or a GUI client.
4. Run the following commands to import each SQL file:
   ```sh
   mysql -u your_mysql_username -p dp_management < appeal.sql
   mysql -u your_mysql_username -p dp_management < class.sql
   mysql -u your_mysql_username -p dp_management < log.sql
   mysql -u your_mysql_username -p dp_management < operation.sql
   mysql -u your_mysql_username -p dp_management < student.sql
   mysql -u your_mysql_username -p dp_management < teacher.sql
    ```
Replace your_mysql_username with your actual MySQL username. You will be prompted to enter your MySQL password after running each command.

### Configure Application
Before running the application, make sure to configure the `application.yml` file:
```yaml
# datasource configuration
spring:
  datasource:
    # ...
    druid:
      url: jdbc:mysql:///dp_management # Customized MySQL host and port
      username: YOUR_MYSQL_USERNAME
      password: YOUR_MYSQL_PASSWORD
  data: # Redis configuration
    redis:
      host: 127.0.0.1 # Customized Redis host
      port: 6380 # Customized Redis port
      password: YOUR_REDIS_PASSWORD # Customized Redis password (if any)
# ...
```

## License
[Apache License](LICENSE)