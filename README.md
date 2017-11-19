# How to Use

## User

### Create user

You can create an user under *<host>*:*<port>*/user/create?username=*<username>*.
If an user with such *username* exists, it will return that the user already exists.

###### Example

\> localhost:8080/user/create?username=tom 

\> User created! Id: 1865114686957133427

Will create the user "tom" and it will return it's id.

## Game

### Create a game

You can create a game under *<host>*:*<port>*/game/create?userId=*<userId>* where the *userId* is the user's id as returned by the create user method. If the player is in a currently online game, it will return that the player is already in a game.

###### Example

\> localhost:8080/game/create?userId=1865114686957133427

\> Game created! Addr: http://localhost:8081/ Id: 3184404184945847092

Will create the user "tom" and it will return it's id.

