Feature: Manage Dependents in HRMS Application

  Background:
    Given ESS user logs in credentials from create login excel row 1

  @regression @dependents
  Scenario: ESS user can access dependents section and view required fields
    When user navigates to My Info dependents page
    Then dependents section is displayed
    And dependent form fields are displayed
    And dependents list columns are displayed

  @regression @dependents
  Scenario: ESS user adds a dependent successfully with Child relationship
    When user navigates to My Info dependents page
    And user clicks add dependent button
    And user enters dependent details from excel row 1
    And user clicks save button on dependents page
    Then dependent is added successfully
    And added dependent details are displayed in the dependents list
    And dependent record is saved in database

  @regression @dependents
  Scenario: ESS user adds a dependent successfully with Other relationship
    When user navigates to My Info dependents page
    And user clicks add dependent button
    And user enters dependent details from excel row 2
    And user clicks save button on dependents page
    Then dependent is added successfully
    And added dependent details are displayed in the dependents list
    And dependent record is saved in database

  @regression @dependents
  Scenario: Required field errors are displayed for missing dependent information
    When user navigates to My Info dependents page
    And user clicks add dependent button
    And user enters dependent details from excel row 3
    And user clicks save button on dependents page
    Then dependent required field errors are displayed

  @regression @dependents
  Scenario: Please Specify field is displayed and required when relationship is Other
    When user navigates to My Info dependents page
    And user clicks add dependent button
    And user enters dependent details from excel row 4
    Then please specify field is displayed
    And user clicks save button on dependents page
    Then please specify required field error is displayed

  @regression @dependents
  Scenario: ESS user edits dependent successfully
    When user navigates to My Info dependents page
    And user edits dependent using excel row 5
    And user clicks save button on dependents page
    Then dependent changes are updated successfully
    And updated dependent details are displayed in the dependents list
    And updated dependent record is saved in database

  @regression @dependents
  Scenario: ESS user deletes dependent successfully
    When user navigates to My Info dependents page
    And user deletes dependent from excel row 6
    Then dependent is removed successfully