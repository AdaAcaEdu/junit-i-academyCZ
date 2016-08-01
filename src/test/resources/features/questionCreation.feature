Feature: question creation

  Background:
    Given user opened browse and went to "localhost:8080"
    And user logged in

  Scenario: create text question
    When user creates text question "This is text question"
    Then  question "This is text question" appears as last in interview
    Then close browser

  Scenario: create radio question
    When user creates radio question "This is radio question"
    And user adds answer "This is radio answer" to question
    Then question "This is radio question" appears as last in interview
    And answer "This is radio answer" appears as last in question
    Then close browser

  Scenario: create checkbox question
    When user creates checkbox question "This is checkbox question"
    And user adds answer "This is checkbox answer" to question
    Then question "This is checkbox question" appears as last in interview
    And answer "This is checkbox answer" appears as last in question
    Then close browser
