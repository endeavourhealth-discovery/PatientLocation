Feature: EpisodeLogic
  Scenario: isActive of null encounter
    Given a null encounter
    When the active status is checked
    Then isActive will be false

  Scenario: isActive of encounter with period but no end date
    Given an encounter
    And it has a period
    And its start date is 25-Jan-2017
    And its end date is null
    And its status is ARRIVED
    When the active status is checked
    Then isActive will be true

  Scenario: isActive of encounter with end date
    Given an encounter
    And it has a period
    And its start date is 25-Jan-2017
    And its end date is 17-Feb-2017
    And its status is ARRIVED
    When the active status is checked
    Then isActive will be false

  Scenario Outline: isActive of encounter with status
    Given an encounter
    And its status is <status>
    When the active status is checked
    Then isActive will be <expected>
    Examples:
    | status      | expected |
    | PLANNED     | false    |
    | ARRIVED     | true     |
    | INPROGRESS  | true     |
    | ONLEAVE     | false    |
    | FINISHED    | false    |
    | CANCELLED   | false    |

  Scenario: isActive of encounter with active status and no end date
    Given an encounter
    And it has a period
    And its status is ARRIVED
    And its end date is null
    When the active status is checked
    Then isActive will be true

  Scenario: isActive of encounter with active status and end date
    Given an encounter
    And it has a period
    And its status is ARRIVED
    And its end date is 17-Feb-2017
    When the active status is checked
    Then isActive will be false

  Scenario: group of null encounter
    Given a null encounter
    When the group is checked
    Then group will be Outpatient

  Scenario Outline: group of encounter with class
    Given an encounter
    And its class is <class>
    When the group is checked
    Then group will be <expected>
    Examples:
      | class      | expected   |
      | INPATIENT  | Inpatient  |
      | AMBULATORY | A&E        |
      | EMERGENCY  | A&E        |
      | OUTPATIENT | Outpatient |
      | HOME       | Outpatient |
      | FIELD      | Outpatient |
      | DAYTIME    | Outpatient |
      | VIRTUAL    | Outpatient |
      | OTHER      | Outpatient |
      | NULL       | Outpatient |

  Scenario: problem of null encounter
    Given a null encounter
    When the problem is checked
    Then problem will be null

  Scenario: problem of encounter with no reason
    Given an encounter
    And its reason is empty
    When the problem is checked
    Then problem will be null

  Scenario: problem of encounter with reason text
    Given an encounter
    And it has a reason
    And its reason has text Asthma
    When the problem is checked
    Then problem will be Asthma

  Scenario: problem of encounter with reason coding display
    Given an encounter
    And it has a reason
    And its reason has coding
    And its reason coding has display Diabetes
    When the problem is checked
    Then problem will be Diabetes

  Scenario: problem of encounter with reason coding code
    Given an encounter
    And it has a reason
    And its reason has coding
    And its reason coding has code H33
    When the problem is checked
    Then problem will be H33

  Scenario: problem of encounter with reason text, coding display & code
    Given an encounter
    And it has a reason
    And its reason has text Asthma
    And its reason has coding
    And its reason coding has display Diabetes
    And its reason coding has code H33
    When the problem is checked
    Then problem will be Asthma