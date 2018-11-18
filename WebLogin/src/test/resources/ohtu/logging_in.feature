Feature: As a registered user can log in with valid username/password-combination

Scenario: user can log in with correct password
Given login is selected
When correct username "jukka" and password "akkuj" are given
Then user is logged in

Scenario: user cannot log in with incorrect password
Given login is selected
When correct username "jukka" and incorrect password "wrong" are given
Then user is not logged in and error message is given

Scenario: nonexistent user cannot log in
Given login is selected
When nonexistent username "arto" and password "akkuj" are given
Then user is not logged in and error message is given
