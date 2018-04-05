# InciManager_i2a

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c0c920d4630d42c3ac4e70dd6844715a)](https://www.codacy.com/app/jelabra/InciManager_i2a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciManager_i2a&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/InciManager_i2a.svg?branch=master)](https://travis-ci.org/Arquisoft/InciManager_i2a)
[![codecov](https://codecov.io/gh/Arquisoft/InciManager_i2a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciManager_i2a)


Incidents Manager module

# 17-18 Authors
- [Marcos Chacón Cadenas](https://github.com/chacon11)
- [Alba Cotarelo Tuñón](https://github.com/albacotarelo)
- [Javier Díez García](https://github.com/javicodema)

# Get started with the application

### **1. Software needed**
You will need to have installed in your computer the following software products:
 - [Maven](https://maven.apache.org/install.html)
 - [MongoDB](https://github.com/Arquisoft/participants_i2b/wiki/MongoDB)
 - [Apache Kafka & Zookeeper](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.0.1/kafka_2.11-1.0.1.tgz)
	
Optional tools:
	- [Robomongo](https://robomongo.org/)
 
### **2. Run Apache Kafka (and Zookeeper)**
You will need to run Apache Kafka and Zookeeper to start the application. You should go to the directory where you have stored Apache Kafka and run two commands from the command prompt (if you are running it in a Windows computer):

 > bin\windows\zookeeper-server-start.bat config/zookeeper.properties
 
 > bin\windows\kafka-server-start.bat config/server.properties
	
### **3. Run the database**
	Open command prompt in the bin folder where you have MongoDB, the path will be similar to this
		> ~\MongoDB\Server\3.6\bin
	Execute:
		> mongod.exe --dbpath <your database path>
	Replace <your database path> with the path to the directory where you want to create your database.
	
### **4. Insert JSON documents in MongoDB database**
	Open command prompt int the bin folder where you have MongoDB, the path will be similar to this
		> ~\MongoDB\Server\3.6\bin
	Execute the following command:
		> mongoimport --db aswdb --collection users --file <your file name>.json
	Where "aswdb" is the name of the database, "users" is the name of the collection and "your file name" 
	is the path where you have your JSON.
	You can check that the document has been added to the database by running Robomongo and going to 
	the corresponding collection of the database.
	
	Notes: MongoDB stores documents in collections. Collections are analogous to tables in relational databases.
	
### **5. Run the project**
	Download the project and go to its directory, open there a command prompt and execute:
		> mvn spring-boot:run
	Now the application is running if all it's correct.
	
### **6. Start using the application**
	Open http://localhost:8080/ on you navigator and login to use the application. To test it, you can follow
	the steps described in the next point.

### **7. Test the application**
	To run the tests just use `mvn test`. No data in the database is needed to run them. But if you want to test the
	user interface manually you'll have to introduce this document:

```json
{
    "id" : ObjectId("58a8670df086e81dc034d7fc"),
    "name" : "Prueba01",
    "location" : "Aviles",
    "email" : "prueba01@prueba.es",
    "password" : "khZZwjdhWwVbMdmOvz9eqBfKR1N6A+CdFBDM9c1dQduUnGewQyPRlBxB4Q6wT7Cq",
    "userId" : "55555555A",
    "kind" : "PERSON",
    "kindCode" : "1"
}
```

And as data use:
 - login: Prueba01
 - password: 4[[j[frVCUMJ>hU

 If you have the data and the database running and the application still reports a 404 Not Found when it shouldn't
 try deleting the document and adding it again.
 
 # More information

## MongoDB
This project uses MongoDB as the database. You can check how to use it on
 - [MongoDB install](https://github.com/Arquisoft/participants_i2b/wiki/MongoDB)

## Jasypt
This project uses Jasypt to encrypt the passwords. You don't need to download it, but you can check it at: http://www.jasypt.org/

## Apache Kafka and Zookeeper
This project uses Apache Kafka and Zookeeper to send messages in runtime to other modules.
You can learn more at: (https://kafka.apache.org/quickstart)
 
