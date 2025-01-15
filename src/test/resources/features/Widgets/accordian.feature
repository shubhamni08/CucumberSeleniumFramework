Feature: Validate Accordian Section

  Scenario: Verify Accordian functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Widgets" section
    And I click on "Accordian"
    Then the content of "What is Lorem Ipsum?" should be displayed

    When I expand the "Where does it come from?" section
    Then the content of "Where does it come from?" should be displayed

    When I expand the "Why do we use it?" section
    Then the content of "Why do we use it?" should be displayed