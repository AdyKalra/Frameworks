@api
Feature: CCMSI API Mobile Example Features
	In order to provide an example of CCMSI Api Mobile
	As a user
	I want to try it out against a prototype API

Scenario Outline: CCMSI Get Paginated Client List
 	Given an access token
 	When I search for userId "kthurman"
 	Then the result contains <clientValue> and <clientName>
 
 	Examples: 
 	| clientValue | clientName											  |
 	| 001         | Nacco (001)											  |
	| 002		  | CONSOLIDATED CONSTRUCTION SAFETY FUND OF ILLINOIS (002)|		

