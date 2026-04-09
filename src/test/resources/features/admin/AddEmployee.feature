Feature: Adding an Employee to the HRMS Application

  Background:
    Given user is on HRMS login page
    When user enters admin credentials from login excel row 1
    And user clicks on login button

  @regression @addEmployee
  Scenario: Admin adds an employee without providing an employee ID
    When user navigates to add employee page
    And user enters add employee details from excel row 1
    And system generates employee ID automatically
    And user clicks save button on add employee page
    Then employee is added successfully
    And added employee record is saved in database

  @regression @addEmployee
  Scenario: Admin adds an employee with employee ID
    When user navigates to add employee page
    And user enters add employee details from excel row 2
    And entered employee ID replaces auto generated employee ID
    And user clicks save button on add employee page
    Then employee is added successfully
    And added employee record is saved in database

  @regression @addEmployee
  Scenario: System shows required error when first name is missing
    When user navigates to add employee page
    And user enters add employee details from excel row 3
    And user clicks save button on add employee page
    Then first name required error is displayed

  @regression @addEmployee
  Scenario: System shows required error when last name is missing
    When user navigates to add employee page
    And user enters add employee details from excel row 4
    And user clicks save button on add employee page
    Then last name required error is displayed

