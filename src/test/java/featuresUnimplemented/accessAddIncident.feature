#language: en
Feature: send incident page 
  Scenario: client makes call to GET /incident/add
    When the client calls /incident/add
    Then the client receives status code of 200
    And the client receives the string "Incidence information"
