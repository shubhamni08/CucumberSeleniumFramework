Feature: Validate Links Section

  Scenario: Verify functionality of Links Page
  Verify redirection to Home
    Given I am on the Links page
    When I click on the "Home" link
    Then I should be redirected to "https://demoqa.com/"

  Scenario: Verify API response for Created link
    Given I am on the Links page
    When I click on the "Created" link
    Then I should see the API response message "201 Created"