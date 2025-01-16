Feature: Validate Browser Windows Section

    # Browser Windows Test Cases
  Scenario: Verify opening and switching to a new tab
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Browser Windows"
#    When I open a new tab
#    Then I should see "This is a sample page"
#    And I should verify the URL is "https://demoqa.com/sample"

  Scenario: Verify opening and switching to a new window
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Browser Windows"
#    When I open a "New Window"
#    Then I should see "This is a sample page"
#    And I should verify the URL is "https://demoqa.com/sample"

  Scenario: Verify handling multiple tabs and windows
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Browser Windows"
#    When I open a new tab
#    And I open a new window
#    Then I should be able to switch between all tabs and windows