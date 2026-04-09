Feature: Create Login Details for Employee

  Background:
    Given user is on HRMS login page
    When user enters admin credentials from login excel row 1
    And user clicks on login button

  @regression @createLogin
  Scenario: Admin can access Add User page and view required fields
    When user navigates to add user page
    Then add user page is displayed
    And all required create login fields are displayed

  @regression @createLogin
  Scenario: Required field validation on Add User page
    When user navigates to add user page
    And user submits create login form from excel row 4
    Then required field errors are displayed for create login form

  @regression @createLogin
  Scenario: Employee name supports search and selection
    When user navigates to add user page
    And user types employee name from create login excel row 1
    Then employee suggestions are displayed
    And user selects employee from create login excel row 1

  @regression @createLogin
  Scenario: Password rules are validated
    When user navigates to add user page
    And user fills create login form from excel row 3
    Then password rule validation messages are displayed
    And password strength indicator is displayed

  @regression @createLogin
  Scenario: Confirm password must match password
    When user navigates to add user page
    And user fills create login form from excel row 2
    Then password mismatch error is displayed

  @regression @createLogin
  Scenario: Admin creates ESS login successfully for an existing employee
    When user navigates to add user page
    And user fills create login form from excel row 1
    And password strength indicator should be strong
    And user saves create login form
    Then ESS user is created successfully
    And created ESS user is linked to selected employee from excel row 1