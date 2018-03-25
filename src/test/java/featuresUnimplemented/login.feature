#language: en
Feature: A user wants to log into the module

  Scenario: Login
   Given a list of users:
      | name    | password |	kindCode	|
      | pepe    | pepe12   |	1 |	
      | sensor  | sensor1	 | 	3	|
    When I login with name "sensor" and password "sensor1" and kindCode 3
    Then I receive a welcome message
