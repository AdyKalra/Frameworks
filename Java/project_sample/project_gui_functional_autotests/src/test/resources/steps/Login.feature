@Inbox @Batch2
Feature: Login feature

  @LogoutFromMailPageHeaderAfter
  Scenario: Check login from the main page
    Given I am on the main page
    When I login as "yatestexample@yandex.ru" user from main page
    Then Mail page logo is displayed

    Scenario: Failed Scenario for report check
      Given I am on the mail page
      When Fail