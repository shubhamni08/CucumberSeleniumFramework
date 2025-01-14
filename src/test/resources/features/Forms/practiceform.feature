Feature: Automation Practice Form Submission

  Scenario: Submit the practice form with valid data
    Given I am on the DemoQA homepage
    When I navigate to the "Forms" section
    And I click on "Practice Form"
    When I fill out the form with valid details:
      | firstName     | Test                |
      | lastName      | User                |
      | Email         | test.user@mail.com  |
      | Gender        | Male                |
      | Mobile Number | 1234567890          |
      | Date of Birth | 01 Jan 1990         |
      | Hobbies       | Reading, Music      |
      | Address       | 123 Main St, City   |


    And I submit the form
    Then I should see a confirmation message or the table should reflect the submission details

