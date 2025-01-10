Feature: Validate Tabs Section

  Scenario: Verify Tabs functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Widgets" section
    And I click on "Tabs"
    When I click on the "What" tab
    Then the content of the "what" tab should be displayed

    When I click on the "Origin" tab
    Then the content of the "origin" tab should be displayed

    When I click on the "Use" tab
    Then the content of the "use" tab should be displayed

    When I click on the "More" tab it is disabled