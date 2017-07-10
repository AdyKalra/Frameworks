@api
Feature: CCMSI API Mobile Example Features For Claim
	In order to provide an example of CCMSI Api Mobile
	As a user
	I want to try it out against a prototype API
@claims @ccmsi
Scenario Outline: Ccmsi Get Paginated Claim List
 	Given an access token
 	When I search for userId 'kthurman' and clientNumber '388'
 	Then the result must contains <claimNumber> and <adjusterEmail>
 
 	Examples: 
 	| claimNumber | adjusterEmail|
 	| F148576     | epenaloza@ccmsi.com |
	

