Feature: Validate Buttons Page Section
  Scenario: Verify buttons positive functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Buttons"
    When I click on "Double Click Me" button
    Then the message "You have done a double click" should be displayed

    When I click on "Right Click Me" button
    Then the message "You have done a right click" should be displayed

#    When I click on "Click Me" button
#    Then the message "You have done a dynamic click" should be displayed
