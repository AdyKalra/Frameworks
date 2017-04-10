@Inbox @Batch1
Feature: Inbox feature

  @LogoutFromMailPageHeaderAfter @777
  Scenario: Check that letter in inbox with selected name is displayed
    Given I am on the mail page
    When I login as "yatestexample@yandex.ru" user from mail page
    When I open letter with title "Как читать почту с мобильного"
    Then Letter title is displayed and valid
    And Sender mail is "hello@yandex-team.ru"
    And Letter body is displayed
