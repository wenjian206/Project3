Feature: A user can post follow another user

Background: A User is on the login page
		Given the User is logged in on the login page
		When the User selects ther Login page
		And the User inputs "nickmontyando@gmail.com" into the username field
		And the User Owner inputs "myPass" into the password field
		And the User Owner clicks login
		Then the User is directed to the homepage

Scenario: User can only view a post for users they follow
    Given the user is successfully logged in
    When The homepage loads
    Then The user can only view posts from users they follow