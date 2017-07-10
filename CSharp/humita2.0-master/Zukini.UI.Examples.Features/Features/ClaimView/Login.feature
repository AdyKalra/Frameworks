@login
Feature: Login
    In order to access my account
	As a user of the website
	I want to log into the website
@claimview
Scenario: Logging in with valid credentials
	Given I am at the login page
	When I fill in the following form
	| field | value |
	| Email | matias.alfonso@jbknowledge.com |
	| Password | Maipu671 |
	And I click the login button
	Then I should be at the Verification Page
@claimview
Scenario: Failed login due to incorrect username or password
    Given I am at the login page
	When I fill in the following form
	| field | value |
	| Email | xtrumanx |
	| Password | P@55w0Rd |
	And I click the login button
    Then I should see "User name or password incorrect."
    
#Scenario: Failed login due to empty username or password fields
#    Given I am at the login page
#	And I click the login button
#    Then I should see "The email is required"
#	And I should see "The password is required"