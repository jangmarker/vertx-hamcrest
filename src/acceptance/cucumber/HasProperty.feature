Feature: Properties of JsonObjects
  Scenario: Has a property
    Given I have {"question" : 42}
    Then it should have the property question
