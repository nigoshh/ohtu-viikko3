Feature: User can log in with valid username/password-combination

    Scenario: user can log in with correct password
       Given command login is selected
       When  username "pekka" and password "akkep" are entered
       Then  system will respond with "logged in"

    Scenario: user cannot log in with incorrect password
       Given command login is selected
       When  username "pekka" and password "wrong" are entered
       Then  system will respond with "wrong username or password"

   Scenario: nonexistent user cannot log in
       Given command login is selected
       When  username "none" and password "password" are entered
       Then  system will respond with "wrong username or password"
