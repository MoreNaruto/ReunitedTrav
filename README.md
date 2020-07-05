# ReunitedTrav

## Set up

### Create MYSQL Database

- Download mysql using this [link](https://dev.mysql.com/doc/mysql-getting-started/en/)
- Once downloaded, go to your terminal and eventType in:
```shell script
mysql -u root -p #Make up a password
``` 
- You should be in the mysql prompt. 
- Once there, eventType in:
```shell script
CREATE DATABASE reunion_travel
```

- Create a new file `application.properties` in your resources directory
- Copy the content from `application.properties.dist` into this file
- Update these fields with appropriate values:
```properties
spring.datasource.username=
spring.datasource.password=
```
