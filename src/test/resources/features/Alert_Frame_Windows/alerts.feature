Feature: Validate Alert Section

    # Alerts Test Cases
  Scenario: Verify dismissing a confirm alert
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I click on "alertButton" click me Button
    Then I should see the confirmation message "You selected Cancel"

    When I trigger the confirm alert and accept it
    Then I should see the confirmation message "You selected Ok"

  Scenario: Verify handling alerts with unexpected behavior
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Alerts"
    When I trigger a missing or disabled alert
    Then I should handle the error gracefully