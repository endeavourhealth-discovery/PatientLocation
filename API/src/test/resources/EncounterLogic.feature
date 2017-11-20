Feature: EncounterLogic - problem
  Scenario: Get open episodes - no allowed services, null requested services
    Given a security context
    And a requested organisation list of null
    When ongoing encounters are retrieved
    Then ongoing encounters list will be null

  Scenario: Get open episodes - no allowed services, invalid requested services
    Given a security context
    And a requested organisation list of 12345
    When ongoing encounters are retrieved
    Then ongoing encounters list will be null

  Scenario: Get open episodes - allowed services, invalid requested services
    Given a security context containing 12345
    And a requested organisation list of 54321
    When ongoing encounters are retrieved
    Then ongoing encounters list will be null

  Scenario: Get open episodes - allowed services, valid requested services, no patients
    Given a security context containing 12345
    And a requested organisation list of 12345
    When ongoing encounters are retrieved
    Then ongoing encounters list will be null

  Scenario: Get open episodes - allowed services, valid requested services, some patients, no episodes
    Given a security context containing 12345
    And a requested organisation list of 12345
    And no ongoing encounters exist
    When ongoing encounters are retrieved
    Then ongoing encounters list will be null

  Scenario: Get open episodes - allowed services, valid requested services, some patients, some episodes
    Given a security context containing 12345
    And a requested organisation list of 12345
    And some ongoing encounters exist
    When ongoing encounters are retrieved
    Then ongoing encounters list will be populated