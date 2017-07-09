@UI
Feature: Add HorseRacing Bet
	
@Screenshot
Scenario: Add Horse Racing Bet
    Given I am navigated to 'williamhill' url
	Then  I click on RaceList
	Then I click on Horse Racing
	Then I Click on England-ascot Race6
	Then I select Kaspersky
	Then I place a bet of '$10.5' and add bet to slip
	Then I click on BetSlip and verify bet is added correctly