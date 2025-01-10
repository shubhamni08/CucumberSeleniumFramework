Feature: Validate Model Dialogs Section

  # Modal Dialogs Test Cases
  Scenario: Verify small modal dialog content
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Modal Dialogs"
    When I open the "Small modal" dialog
    Then I should see the dialog content as "This is a small modal. It has very less content"

  Scenario: Verify large modal dialog content
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Modal Dialogs"
    When I open the "Large modal" dialog
    Then I should see the dialog content starting with "Lorem Ipsum"

  Scenario: Verify closing a modal dialog
    Given I am on the DemoQA homepage
    When I navigate to the "Alerts, Frame & Windows" section
    And I click on "Modal Dialogs"
    When I open the "Large modal" dialog
    Then I should be able to close it by clicking the "Close" button