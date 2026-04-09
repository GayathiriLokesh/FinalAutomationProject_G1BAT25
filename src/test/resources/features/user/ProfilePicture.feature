Feature: Profile Picture Upload for ESS Users

  Background:
    Given user is on HRMS login page
    When user enters ESS credentials from create login excel row 1
    And user clicks on login button
    And ESS user navigates to My Info page

  @regression @profilePicture
  Scenario: ESS user uploads valid jpg profile picture successfully
    When ESS user uploads profile picture from excel row 1
    And ESS user clicks upload button
    Then profile picture is uploaded successfully

  @regression @profilePicture
  Scenario: ESS user uploads valid png profile picture successfully
    When ESS user uploads profile picture from excel row 2
    And ESS user clicks upload button
    Then profile picture is uploaded successfully

  @regression @profilePicture
  Scenario: ESS user uploads valid gif profile picture successfully
    When ESS user uploads profile picture from excel row 3
    And ESS user clicks upload button
    Then profile picture is uploaded successfully

  @regression @profilePicture
  Scenario: ESS user gets validation for unsupported profile picture format
    When ESS user uploads profile picture from excel row 4
    And ESS user clicks upload button
    Then unsupported profile picture format error is displayed

  @regression @profilePicture
  Scenario: ESS user gets validation for oversized profile picture file
    When ESS user uploads profile picture from excel row 5
    And ESS user clicks upload button
    Then profile picture size error is displayed