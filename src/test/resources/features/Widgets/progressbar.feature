Feature: Validate Progress Bar Section

  Scenario: Verify Progress Bar functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Widgets" section
    And I click on "Progress Bar"
    When I click on the "Start" button
    Then the progress bar should reach "100%"

    When I click on the "Reset" button
    And I click on the "Start" button
    And I click on the "Stop" button
    Then I get progress bar value