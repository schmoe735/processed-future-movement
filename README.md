# Processed Future Movement

Application for processing daily transaction data from futures trading. 
The transactions are cached in memory and served as daily summary report through REST api.

# API

* /api/v1/report/daily-summary/{client-id} - Retrieve a summary report for the given client id



### Libraries Used

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Lombok](https://projectlombok.org/)

