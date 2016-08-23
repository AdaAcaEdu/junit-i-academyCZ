Feature: question creation

  Background:
    Given user opens browser
    And user navigates to homepage
    And user logs in

  Scenario Outline: Test radio, checkbox and text question creation
    When user creates <questionText> question of type <typeIndex>
    Then <questionText> question appears as last in interview

    Examples:
      | questionText | typeIndex |
      | "This is question with checkbox" | 0 |
      | "This is question with textarea" | 1 |
      | "This is question with radio" | 2 |

  Scenario Outline: Test radio and checkbox answer creation
    Given user creates <questionText> question of type <typeIndex>
    When user adds <answerText> answer to question
    Then <answerText> answer appears as last in question

    Examples:
      | questionText | answerText | typeIndex |
      | "This is question with checkbox" | "This is checkbox answer" | 0 |
      | "This is question with radio" | "This is radio answer" | 2 |

  #Assignment

  Scenario: Test question deletion
    Given user creates "This is question with checkbox" question of type 0
    When user deletes last question
    Then number of questions decreases by one

  Scenario: Test answer deletion
    Given user creates "This is question with checkbox" question of type 0
    And user adds "This is checkbox answer" answer to question
    When user deletes last answer in last question
    Then number of answers in last question will be zero

  #End of assignment