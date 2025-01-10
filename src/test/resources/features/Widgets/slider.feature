Feature: Validate Slider Section

  Scenario: Verify Slider functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Widgets" section
    And I click on "Slider"
    When I move the slider to "50"
    Then the slider value should be "50"