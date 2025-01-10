Feature: Validate Validate Upload & Download Section

  Scenario: Verify Download functionality
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Upload and Download"
    When I download a file
    Then the file should be downloaded in the local folder

    When I upload a file
    Then the uploaded file path should be displayed











