Feature: EpisodeLogic - problem
  Scenario: Get open episodes - no allowed services, null requested services
    Given a security context
    And a requested organisation list of null
    When open episodes are retrieved
    Then open episodes list will be null

  Scenario: Get open episodes - no allowed services, invalid requested services
    Given a security context
    And a requested organisation list of 12345
    When open episodes are retrieved
    Then open episodes list will be null

  Scenario: Get open episodes - allowed services, invalid requested services
    Given a security context containing 12345
    And a requested organisation list of 54321
    When open episodes are retrieved
    Then open episodes list will be null

  Scenario: Get open episodes - allowed services, valid requested services, no patients
    Given a security context containing 12345
    And a requested organisation list of 12345
    And there are none of my patients at other organisations
    When open episodes are retrieved
    Then open episodes list will be null

  Scenario: Get open episodes - allowed services, valid requested services, some patients, no episodes
    Given a security context containing 12345
    And a requested organisation list of 12345
    And there are some of my patients at other organisations
    And no episodes of care exist
    When open episodes are retrieved
    Then open episodes list will be empty

  Scenario: Get open episodes - allowed services, valid requested services, some patients, some episodes
    Given a security context containing 12345
    And a requested organisation list of 12345
    And there are some of my patients at other organisations
    And some episodes of care exist
    When open episodes are retrieved
    Then open episodes list will be populated