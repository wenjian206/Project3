Feature: A user can follow another user

Background: A User is on the login page
		Given the User is logged in on the login page
		When the User selects ther Login page
		And the User inputs "nickmontyando@gmail.com" into the username field
		And the User Owner inputs "myPass" into the password field
		And the User Owner clicks login
		Then the User is directed to the homepage

Scenario: Follow a User
    Given the user is successfully logged in
    And The homepage loads
    When The user clicks on a user
    And The users profile page loads
    When The user clicks on follow
    Then The user will be following another user