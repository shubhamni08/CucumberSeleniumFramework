Feature: Validate Links Section

  Scenario: Verify functionality of Links Page
  Verify redirection to Home
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Links"
    When I click on the "Home" link
    Then I should be redirected to "https://demoqa.com/"

  Scenario: Verify API response for Created link
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Links"
    When I click on the "Created" link
    Then I should see the API response message "201 Created"

  Scenario: Verify API response for Forbidden link
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Links"
    When I click on the "Forbidden" link
    Then I should see the API response message "403 Forbidden"