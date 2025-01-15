Feature: Validate Radio Button Section

  Scenario: Verify RadioButton functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Radio Button"
    When I select the "Yes" radio button
    Then the message You have selected "Yes" should be displayed

    When I select the "Impressive" radio button
    Then the message You have selected "Impressive" should be displayed

    When I select the "No" radio button
    Then the "No" radio button is disabled