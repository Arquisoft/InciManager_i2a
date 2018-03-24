#language: en
Feature: A user wants to log into the module, but its credentials are not okay

  Scenario: Login failure
   Given a list of users:
      | name    | password |	kindCode	|
      | pepe    | pepe12   |	1 |	
      | sensor  | sensor1	 | 	3	|
    When I login with name "sensor" and password "sensor4" and kindCode 3
    Then I go back to the login page
