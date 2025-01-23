Feature: Books API Testing

  Scenario: Get list of books
    Given I am on the DemoQA homepage
    When I navigate to the "Book Store Application" section
    And I click on "Book Store"
    Then I get the list of books