Feature: Repository List Search Page Validation

  Scenario: Initial UI Validation
    Given user is on the repository list page
    Then Page title is "Git Repository List"
    And Databricks logo should be displayed
    And Page Header is "Repository List"
    And Search box should be displayed
    And Following columns should be present in the table
    |Name   |
    |Owner  |
    |Stars  |
    |Link   |
    |Details|
    And No Data Found message should be displayed
    And Rows per page dropdown should be displayed with below values
    |10|
    |25|
    |50|

  Scenario: Validate the filter results
    Given user is on the repository list page
    Given Enter the value "kube" in the search box
    Then Validate all the filtered results name should has the "kube"
    And only "10" rows should display and all the values should be not null
    And stars value should be the number
    And link values should be the combination of owner and name values
    When Click on the random link
    Then Github page should opens in the new tab
    When hover over on the details icon
    Then Get Details text should be displayed
    When Click on the details icon
    Then Validate the repo details header.it should contains the combination of name and owner of the repository
    And Last 3 committers label should be displayed with values
    And Recent Forked User label should be displayed with value
    And Recent Forked User Bio label should be displayed
    When Click on the close icon
    Then Repo details dialog should not be displayed
    When Click on the details icon
    And Click on the OK button
    Then Repo details dialog should not be displayed

  Scenario: Pagination Validation
    Given user is on the repository list page
    Given Enter the value "kube" in the search box
    Given By default pagination value should be "10";
    Then "10" data rows should be displayed
    When Select the value of "25"
    Then "25" data rows should be displayed
    When Select the value of "50"
    Then "50" data rows should be displayed
    When Select the value of "10"
    Then "10" data rows should be displayed
    And Pagination "Previous" button should be "disabled"
    And Pagination "Next" button should be "enabled"
    When Click on the pagination Next button
    Then "10" data rows should be displayed
    And Pagination "Previous" button should be "enabled"
    And Pagination "Next" button should be "enabled"

  Scenario: Total Count Validation
    Given user is on the repository list page
    Given Enter the value "ZestMoneyAssignment" in the search box
    Then Validate all the filtered results displaying
    And At the last page Pagination "Next" button should be "disabled"