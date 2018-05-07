# InciManager_i2a

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c0c920d4630d42c3ac4e70dd6844715a)](https://www.codacy.com/app/jelabra/InciManager_i2a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciManager_i2a&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/InciManager_i2a.svg?branch=master)](https://travis-ci.org/Arquisoft/InciManager_i2a)
[![codecov](https://codecov.io/gh/Arquisoft/InciManager_i2a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciManager_i2a)
[![Codefresh build status]( https://g.codefresh.io/api/badges/build?repoOwner=Arquisoft&repoName=InciManager_i2a&branch=master&pipelineName=InciManager_i2a&accountName=javicodema&type=cf-1)]( https://g.codefresh.io/repositories/Arquisoft/InciManager_i2a/builds?filter=trigger:build;branch:master;service:5ad478185e7a2500012fdeb5~InciManager_i2a)

Incidents Manager module

# 17-18 Authors
- [Marcos Chacón Cadenas](https://github.com/chacon11)
- [Alba Cotarelo Tuñón](https://github.com/albacotarelo)
- [Javier Díez García](https://github.com/javicodema)

# Get started with the application

### **1. Software needed**
You will need to have installed in your computer the following software products:
 - [Maven](https://maven.apache.org/install.html)
 - [Zookeeper](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.0.1/kafka_2.11-1.0.1.tgz)
 
### **2. Run Zookeeper**
You will need to run Zookeeper to start the application. You should go to the directory where you have stored Apache Kafka and run this command from the command prompt (if you are running it in a Windows computer):

 > bin\windows\zookeeper-server-start.bat config/zookeeper.properties
	
	
### **3. Insert documents in MongoDB database**
	As this module is currently working along other modules, in order to have an agent to test the application, you should run the 
	[Loader](https://github.com/Arquisoft/Loader_i2a) module, which will add the agents you specify in a xls file to the database 		that this module uses.
### **4. Run the project**
	Download the project and go to its directory, open there a command prompt and execute:
		> mvn spring-boot:run
	Now the application is running if all it's correct.
	
### **5. Start using the application**
	Open http://localhost:8081/ on you navigator and login to use the application.

### **6. Test the application**
	To run the tests just use `mvn test`. No data in the database is needed to run them.

 
 # More information

## MongoDB (with mLab)
This project uses MongoDB as the database. You can check how to use it on
 - [MongoDB install](https://github.com/Arquisoft/participants_i2b/wiki/MongoDB)

## Jasypt
This project uses Jasypt to encrypt the passwords. You don't need to download it, but you can check it at: http://www.jasypt.org/

## Apache Kafka and Zookeeper (with CloudKarafka)
This project uses Apache Kafka and Zookeeper to send messages in runtime to other modules.
You can learn more at: (https://kafka.apache.org/quickstart)
 
