Feature: Admin User Login for HRMS Application

  @regression @login
  Scenario: Admin user logs in successfully with valid credentials
    Given admin user is on HRMS login page
    When admin user enters valid username and password from excel row 1
    And admin user clicks login button
    Then admin user should be redirected to admin dashboard