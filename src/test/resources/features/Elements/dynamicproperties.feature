Feature: Validate Dynamic Properties Section

  Scenario: Validate button enabled after 5 seconds
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Dynamic Properties"
    Then the "Will enable 5 seconds" button should be enabled after 5 seconds

    Then the "Visible After 5 Seconds" button should become visible after 5 seconds

  Scenario: Validate color change of the button
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Dynamic Properties"
    Then the "Color Change" button should change color




