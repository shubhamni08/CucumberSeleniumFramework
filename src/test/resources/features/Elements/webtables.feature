Feature: Web Tables Management

  Scenario: Add, Edit, and Delete an Entry in the Web Table
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Web Tables"
    When I click on the "Add" button
    And I fill the registration form with the following details:
      | first name | John    |
      | last name  | Doe     |
      | email      | john.doe@example.com |
      | age        | 30      |
      | salary     | 50000   |
      | department | HR      |
    And I submit the registration form
    Then I verify the entry "John" exists in the table