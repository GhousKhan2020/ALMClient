Feature: Visure ALM Login

  Scenario: Launch Visure ALM Client and validate window
    Given Launch the Visure ALM Client
    Then Validate all the labels and elements

  Scenario: Login with valid credentials when username is already present
    Given Launch the Visure ALM Client
    When I check if the username "admin" is already there
    And I enter a valid password "1234"
    And I select "Agile" project
    And I click the "Login" button
    Then I should be logged in successfully

  Scenario: Login with valid credentials when username is not present
    Given Launch the Visure ALM Client
    When I check if the username "newUser"
    And I enter a valid password "password123"
    And I click the "Login" button
    Then I should be logged in successfully

  Scenario: Validate all elements on the login page
    Given Launch the Visure ALM Client
    Then Validate all the labels and elements

  Scenario: Validate login button is disabled by default
    Given Launch the Visure ALM Client
    Then Validate all the labels and elements

  Scenario: Validate error message on invalid login
    Given Launch the Visure ALM Client
    When I check if the username "invalidUser"
    And I enter a valid password "invalidPassword"
    And I click the "Login" button
    Then I should see an error message saying "Invalid credentials"
