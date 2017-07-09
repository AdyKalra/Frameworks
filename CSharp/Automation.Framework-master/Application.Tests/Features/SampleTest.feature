Feature: SampleTest

Scenario Outline: Validate Sapient Contact Details
	Given I Launch the Google Search Page
	And I search for Sapient Global Markets
	When I Click the 1 Search Result
	Then Validate Sapient Global Markets Page is opened
	When I Click the Contact Menu
	And I Scroll down 
	And I Select Bengaluru Location
	Then Validate Phone Number is <PhoneNumber>
	And Validate Fax Number is <FaxNumber>
	And Validate Email is <Email>

	Examples: 
	| PhoneNumber         | FaxNumber           | Email            |
	| +91 (080) 6128 0000 | +91 (080) 6128 0001 | info@sapient.com |
