Feature: Search for an Employee in the HRMS Application

  Background:
    Given user is on HRMS login page
    When user enters admin credentials from login excel row 1
    And user clicks on login button

  @regression @searchEmployee
  Scenario: Admin searches employee by full name
    When user navigates to employee search page
    And user searches employee by full name from search employee excel row 1
    Then matching employee records are displayed

  @regression @searchEmployee
  Scenario: Admin searches employee by partial name
    When user navigates to employee search page
    And user searches employee by partial name from search employee excel row 2
    Then matching employee records are displayed

  @regression @searchEmployee
  Scenario: Admin searches employee by employee ID
    When user navigates to employee search page
    And user searches employee by ID from search employee excel row 3
    Then matching employee records are displayed

  @regression @searchEmployee
  Scenario: Admin gets no records found for unmatched employee name
    When user navigates to employee search page
    And user searches unmatched employee name from search employee excel row 4
    Then no records found message is displayed

  @regression @searchEmployee
  Scenario: Admin gets no records found for unmatched employee ID
    When user navigates to employee search page
    And user searches employee by ID from search employee excel row 5
    Then no records found message is displayed