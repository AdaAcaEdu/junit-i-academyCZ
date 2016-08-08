Feature: question creation

  Background:
    Given user opens browser
    And user navigates to homepage
    And user logs in

  Scenario Outline: create question
    When user creates <questionText> question of type <typeIndex>
    And user adds <answerText> answer to question
    Then <questionText> question appears as last in interview
    And <answerText> answer appears as last in question
    Then user closes browser

    Examples:
      | questionText | answerText | typeIndex |
      | "This is question with checkbox" | "This is checkbox answer" | 0 |
      | "This is question with textarea" | none | 1 |
      | "This is question with radio" | "This is radio answer" | 2 |
