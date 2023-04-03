Feature: Sign up for Mailchimp

  Scenario Outline: Registration
    Given I start a "<browser>"
    Given I have input a valid "<email>"
    Given I have input a "<username>"
    Given I have input a secure "<password>"
    When I press Sign Up
    Then check email page "<works>"
    Examples:
      | browser | email | username | password | works |
      | chrome  | Jerry | Tom      | Nibbles  | true  |
      | edge    | Jerry | Tom      | Nibbles  | true  |
      | chrome  | Jerry | long     | Nibbles  | f1    |
      | edge    | Jerry | long     | Nibbles  | f1    |
      | chrome  | Jerry | same     | Nibbles  | f2    |
      | edge    | Jerry | same     | Nibbles  | f2    |
      | chrome  |       | Tom      | Nibbles  | f3    |
      | edge    |       | Tom      | Nibbles  | f3    |

