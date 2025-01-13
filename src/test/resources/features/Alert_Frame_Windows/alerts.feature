Feature: Validate Alert Section

    # Alerts Test Cases
  Scenario: Verify simple alert
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I click on "alertButton" click me Button
    Then alert with the message "You clicked a button" should appear
    And I accept the alert

  Scenario: Verify timer alert
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I click on "timerAlertButton" click me Button
    Then alert with the message "This alert appeared after 5 seconds" should appear
    And I accept the alert

  Scenario: Verify confirmation alert
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I click on "confirmButton" click me Button
    Then alert with the message "Do you confirm action?" should appear
    And I accept the alert
    Then the confirmation result should be "You selected Ok"

    When I click on "confirmButton" click me Button
    Then alert with the message "Do you confirm action?" should appear
    And I dismiss the alert
    Then the confirmation result should be "You selected Cancel"

  Scenario: Verify Prompt alert
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I click on "promtButton" click me Button
    Then alert with the message "Please enter your name" should appear

    When I enter "DemoQA User" into the alert
    And I accept the alert
    Then the prompt result should be "You entered DemoQA User"