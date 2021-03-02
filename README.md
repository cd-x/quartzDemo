# quartzDemo
Quartz application with spring boot and thymeleaf and postgresql as persistent datasource


1. Pull application image from docker hub  `$docker pull rrishi633/quartzapp:1.1`
2. Tag image to myapp `$docker tag rrishi633/quartzapp:1.1 myapp`
3. Get the docker-compose file from This repository `$wget https://github.com/cd-x/quartzDemo/docker-compose.yml`
4. Good to go `$docker-compose up`

-------------------------------------------------------------------------

# API Calls

1. Homepage to fill the details for the job to be scheduled 
> localhost:8080/
2. To land on joke page
> localhost:8080/getAJoke
3. To land on Developer Quote page
> localhost:8080/getAQuote
