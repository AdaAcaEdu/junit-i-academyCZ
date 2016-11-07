Feature: admin question creation
  
  As an admin
  I want to be able to create questions
  So that I get desired interview

  Background:
    Given admin opens browser
    And admin navigates to homepage
    And admin logs in

  Scenario Outline: Test radio, checkbox and text question creation
    When admin creates <questionText> question of type <type>
    Then <questionText> question appears as last in interview

    Examples:
      | questionText | type |
      | "This is question with checkbox" | "Checkbox" |
      | "This is question with textarea" | "Text" |
      | "This is question with radio" | "Radio" |

  Scenario Outline: Test radio and checkbox answer creation
    Given admin creates <questionText> question of type <type>
    When admin adds <answerText> answer to question
    Then <answerText> answer appears as last in question

    Examples:
      | questionText | answerText | type |
      | "This is question with checkbox" | "This is checkbox answer" | "Checkbox" |
      | "This is question with radio" | "This is radio answer" | "Radio" |

