Feature: Validate TextBox Section

  Scenario: Verify TextBox positive functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Text Box"
    When I fill the form with:
      | Full Name         | Shubham Nikam   |
      | Email             | nikam@yahoo.com |
      | Current Address   | 123 Main Street |
      | Permanent Address | 456 Side Street |
    Then I click on submit button
    Then the output should display:
      | Field             | Value                               |
      | name              | Name:Shubham Nikam                  |
      | email             | Email:nikam@yahoo.com               |
      | Current Address   | Current Address :123 Main Street    |
      | Permanent Address | Permananet Address :456 Side Street |

  Scenario: Verify TextBox negative functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Text Box"
    And I click on submit button
    Then no output should be displayed
