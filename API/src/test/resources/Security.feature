Feature: Security
  Scenario Outline: Allowed organisations
    Given a security context containing <accessible>
    When the allowed organisations are checked
    Then the user allowed organisations will be <accessible>
    Examples:
    | accessible            |
    | 1234567890,0987654321 |

  Scenario Outline: Allowed access no context
    Given no security context
    And a requested organisation list of <requested>
    When the users allowed access is checked
    Then the user allowed access will be <actual>
    Examples:
      | requested             | actual |
      |                       | false  |
      | 5432167890            | false  |
      | 5432167890,0987654321 | false  |


  Scenario Outline: Allowed access with context
    Given a security context containing <accessible>
    And a requested organisation list of <requested>
    When the users allowed access is checked
    Then the user allowed access will be <actual>
    Examples:
      | accessible            | requested             | actual |
      |                       | 5432167890            | false  |
      | 1234567890,0987654321 |                       | false  |
      | 1234567890,0987654321 | 5432167890            | false  |
      | 1234567890,0987654321 | 5432167890,0987654321 | false  |
      | 1234567890,0987654321 | 1234567890            | true   |
      | 1234567890,0987654321 | 0987654321            | true   |
      | 1234567890,0987654321 | 1234567890,0987654321 | true   |

    Scenario: Users default organisation with no org
      Given a security context
      And no default organisation
      When the users default organisation is checked
      Then the users default organisation will be null

  Scenario: Users default organisation with org
    Given a security context
    And default organisation of 12345
    When the users default organisation is checked
    Then the users default organisation will be 12345