#Assignment

Feature: admin question deletion

  As an admin
  I want to be able to delete questions
  So that I get desired interview

  Background:
    Given admin opens browser
    And admin navigates to homepage
    And admin logs in

  Scenario: Test question deletion
    Given admin creates "This is question with checkbox" question of type "Checkbox"
    When admin deletes last question
    Then number of questions decreases by one

  Scenario: Test answer deletion
    Given admin creates "This is question with checkbox" question of type "Checkbox"
    And admin adds "This is checkbox answer" answer to question
    When admin deletes last answer in last question
    Then number of answers in last question will be zero

#End of assignment
