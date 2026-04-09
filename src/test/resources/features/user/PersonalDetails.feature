Feature: Update Personal Details

   Background:
    Given user is on HRMS login page
    When user enters ESS credentials from create login excel row 1
    And user clicks on login button

  @regression @personalDetails
  Scenario: ESS user can access personal details page and view editable fields
    When ESS user navigates to My Info page
    Then personal details page is displayed
    And personal details fields are displayed

  @regression @personalDetails
  Scenario: ESS user can update personal details successfully
    When ESS user navigates to My Info page
    And ESS user updates personal details from excel row 1
    And ESS user saves personal details
    Then personal details are updated successfully
    And personal details are updated in database