#language: en
Feature: Incident detail page 
  Scenario: client makes call to GET /incident/detail/{id}
    When the client calls /incident/list/{id} being a id an incident id that he has sent
    Then the client receives status code of 200
    And the client receives the string "Incident detail"
