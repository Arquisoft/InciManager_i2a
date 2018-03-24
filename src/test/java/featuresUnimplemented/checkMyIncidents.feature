#language: en
Feature: A user wants to check all the incidents he has sent
  Scenario: Send incident
   Given a list of users:
      | name    | password |	kindCode	|
      | pepe    | pepe12   |	1 |	
      | sensor  | sensor1	 | 	3	|
    When I login with name "sensor" and password "sensor1" and kindCode 3
    And I access to /incident/list
    Then I get a list with my incidents
