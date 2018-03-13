package main;

import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
//@EnableMongoRepositories("dbmanagement")
@ComponentScan({"dbmanagement", "view", "domain", "services"})
public class Application {

   /** public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }**/
}