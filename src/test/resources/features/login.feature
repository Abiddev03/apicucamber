@userAuthenticationMFA
Feature: MFA API

  Scenario: Successful MFA request with valid username
    Given the API base URL is "https://uat-api.updatepromise.com"
    When I make a POST request to "/auth/mfa/get-contact" with username "ffarooquatman"
    Then the response status should be 200
    And the response should contain success with preferred : 2
    And the email should be "f******q@corp.updatepromise.com"
    And the phone should be "(***) ***-**89"



  Scenario: Failed MFA request with invalid username
    Given the API base URL is "https://uat-api.updatepromise.com"
    When I make a POST request to "/auth/mfa/get-contact" with username "invalidUser"
    Then the response status should be 200
    And the response should contain an error with message "User not found."
