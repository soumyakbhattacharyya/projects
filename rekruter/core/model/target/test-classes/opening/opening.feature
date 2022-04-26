Feature: Opening
  Features associated with management of various opening

  Scenario: opening registration
    Given I want to register an opening
    When I register
    Then The opening gets registered
