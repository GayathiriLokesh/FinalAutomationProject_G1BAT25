Feature: Login Error Validation

  Background:
    Given user is on HRMS login page

  @regression @loginValidation
  Scenario: User logs in successfully with valid credentials
    When user enters login credentials from login excel row 1
    And user clicks on login button
    Then user is logged in successfully

  @regression @loginValidation
  Scenario: Correct username and wrong password shows invalid credentials
    When user enters login credentials from login excel row 2
    And user clicks on login button
    Then invalid credentials error is displayed
    And user remains on login page

  @regression @loginValidation
  Scenario: Correct username and empty password shows required error
    When user enters login credentials from login excel row 3
    And user clicks on login button
    Then password required error is displayed
    And user remains on login page

  @regression @loginValidation
  Scenario: Empty username and entered password shows required error
    When user enters login credentials from login excel row 4
    And user clicks on login button
    Then username required error is displayed
    And user remains on login page

  @regression @loginValidation
  Scenario: Empty username and empty password shows required errors for both fields
    When user enters login credentials from login excel row 5
    And user clicks on login button
    Then username required error is displayed
    And password required error is displayed
    And user remains on login page

  @regression @loginValidation
  Scenario: User can re-enter valid credentials after invalid login
    When user enters login credentials from login excel row 2
    And user clicks on login button
    Then invalid credentials error is displayed
    And user remains on login page
    When user enters login credentials from login excel row 1
    And user clicks on login button
    Then user is logged in successfully