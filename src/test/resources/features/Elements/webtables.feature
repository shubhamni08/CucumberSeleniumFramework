Feature: Web Tables Management

  Scenario: Add, Edit, and Delete an Entry in the Web Table
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Web Tables"
    When I click on the "Add" button.
    And I fill the registration form with the following details:
      | firstName  | Test    |
      | lastName   | User    |
      | userEmail  | test.usr@example.com |
      | age        | 30      |
      | salary     | 50000   |
      | department | Finance |
    And I submit the registration form
    Then I verify the entry "John" exists in the table

    When I edit the entry "Test" with the following details:
      | firstName  | Test    |
      | department | AQA     |
    And I submit the registration form
    Then I verify the entry "Test" exists in the table

    When I delete the entry "Johnny"
    Then I verify the entry "Johnny" no longer exists in the table