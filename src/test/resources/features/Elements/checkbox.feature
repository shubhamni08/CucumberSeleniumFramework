Feature: Validate Check Box Page Section
  As a user, I want to interact with the Check Box page and validate its functionalities.

  Scenario: Verify expanding and collapsing tree structure
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Check Box"
    When I click on ExpandAll button
    Then all items in the tree structure should be "visible"
    When I click on CollapseAll button
    Then all items in the tree structure should be "collapsed"

  Scenario: Verify selecting a single checkbox
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Check Box"
    And I click on ExpandAll button
    And I select the "Notes" checkbox
    Then the success message "You have selected : notes" should be displayed

  Scenario: Verify selecting multiple checkboxes
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Check Box"
    And I click on ExpandAll button
    When I select the following checkboxes:
      | Desktop        |
      | Downloads      |
    Then the success message "You have selected : desktop notes commands downloads wordFile excelFile" should be displayed

  Scenario: Verify selecting parent checkbox selects all children
    Given I am on the DemoQA homepage
    When I navigate to the "Elements" section
    And I click on "Check Box"
    When I select the "Home" checkbox
    Then the success message "You have selected : home desktop notes commands documents workspace react angular veu office public private classified general downloads wordFile excelFile" should be displayed