Feature: question creation

  Background:
    Given user opens browser
    And user navigates to homepage
    And user logs in

  Scenario Outline: Test radio, checkbox and text question creation
    When user creates <questionText> question of type <type>
    Then <questionText> question appears as last in interview

    Examples:
      | questionText | type |
      | "This is question with checkbox" | "Checkbox" |
      | "This is question with textarea" | "Text" |
      | "This is question with radio" | "Radio" |

  Scenario Outline: Test radio and checkbox answer creation
    Given user creates <questionText> question of type <type>
    When user adds <answerText> answer to question
    Then <answerText> answer appears as last in question

    Examples:
      | questionText | answerText | type |
      | "This is question with checkbox" | "This is checkbox answer" | "Checkbox" |
      | "This is question with radio" | "This is radio answer" | "Radio" |

  #Assignment

  Scenario: Test question deletion
    Given user creates "This is question with checkbox" question of type "Checkbox"
    When user deletes last question
    Then number of questions decreases by one

  Scenario: Test answer deletion
    Given user creates "This is question with checkbox" question of type "Checkbox"
    And user adds "This is checkbox answer" answer to question
    When user deletes last answer in last question
    Then number of answers in last question will be zero

  #End of assignment