#language: en
Feature: Incident detail page 
  Scenario: client makes call to GET /incident/details/{id}
    When the client calls /incident/details/"5ae98d54c6d5af19b05b049e" being a id an incident id that he has sent
    Then the client receives status code of 200 when accessing details page
