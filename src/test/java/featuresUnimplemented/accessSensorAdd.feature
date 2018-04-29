#language: en
Feature: A sensor wants to access incident/sensorAdd
  Scenario: Send incident
   Given a list of users:
      | name    | password |	kindCode	|
      | pepe    | 4[[j[frVCUMJ>hU   |	1 |	
      | sensor  | 4[[j[frVCUMJ>hU	 | 	3	|
    When I login with name "sensor" and password "sensor1" and kindCode 3
    And I call /incident/add
    And I send an incident with name "I1" and description "" and tags "" and lattitude 0.0 and longitude 0.0 and multimedia ""
    Then I access to /incident/sensorAdd
