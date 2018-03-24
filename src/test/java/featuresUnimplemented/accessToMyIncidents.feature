#language: en
Feature: myIncidents page 
  Scenario: client makes call to GET /incident/list
    When the client calls /incident/list
    Then the client receives status code of 200
    And the client receives the string "Incidents list"
