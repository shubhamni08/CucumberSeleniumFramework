Feature: Automation Practice Form Submission

  Scenario: Submit the practice form with valid data
    Given I am on the DemoQA homepage
    When I navigate to the "Forms" section
    And I click on "Practice Form"
    When I fill out the form with valid details
      | First Name | Last Name | Email             | Gender | Mobile Number | Date of Birth | Subject       | Hobbies      | Picture         | Address         | State        | City         |
      | John       | Doe       | john.doe@mail.com  | Male   | 1234567890    | 01 Jan 1990    | Computer Science | Reading, Music | photo.jpg       | 123 Main St, City | Haryana     | Panipat      |
    And I submit the form
    Then I should see a confirmation message or the table should reflect the submission details

    