Feature: Change Contact Details for ESS User

  @regression @contactDetails
  Scenario: ESS user updates contact details successfully
    Given ESS user logs in credentials from create login excel row 1
    When user navigates to My Info contact details page
    Then contact details form is displayed with all required fields
    And user enters contact details from excel row 1
    And user clicks save button
    Then contact details are updated successfully
