# tranning-java
tranning java

#INTRODUCTION
- This app is example for JAVA SPRING BOOT
- Function
 + Authentication (JPA Security)
 + CRUD Restful API
 + Open API 3.0 (Swagger v3)
 + ELK


#INSTALL APP

Step 1: Config your database in .env file and copy it to application.properties. Config it at env.local file if you want to run in local
Step 2: Run "mvn clean install" and run the app
Step 3: Set up ELK
 - Run "docker-compose up -d" to install Elastic Search and Kibana
 - Run "docker pull docker.elastic.co/logstash/logstash:8.4.1"
 - Run "docker run --rm -it -p 5044:5044 docker.elastic.co/logstash/logstash:8.4.1"
