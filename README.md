# Tic Tac Toe

[![Latest release](https://img.shields.io/badge/Release-1.0.1-blue)](https://github.com/mrodz/TicTacToe-Reimagined/releases)
[![Build Status](https://img.shields.io/badge/Build-Passing-Sucess)]()

Tic Tac Toe is a classic children's game where two players compete to see
who can match three of their symbols in a row before their opponent.  

Here, I've implemented it as a desktop app!

***

### App Breakdown
Upon launching the app, you will be greeted with the main interface. There are four
sections of interest:

* The Control Panel
* The Grid/Play Space
* The Status Bar
* The Sidebar  
  
Let's delve into each of these components with more detail.

#### Control Panel
The control panel is the yellow bubble at the very top of the application. It
is made up of two player selectors, and an action button.  
  
The player selector wheels have a piece of text content in between two arrows,
which can be used to cycle between each playable entity. These are `Human`, `Easy Bot`,
`Medium Bot`, and `Hard Bot`.  
  
Clicking the action button will start a round of Tic Tac Toe, setting players 'X' and 'O'
as their corresponding values in the text wheels.  
  
Once the game has started, the action button will switch to a reset button. If this is clicked,
all game state data will be wiped, and you are given the choice to select new players.  

#### The Playable Zone (Grid)
This is the area painted in purple, and contains a 9x9 grid of cells. Once a round of Tic Tac Toe
has started, the game will loop through each move. If said turn belongs to a bot, it will make a
move automatically; otherwise, the game will wait for a human user to click on a cell. The first 
empty cell person clicks on will be saved as their placement.  
  
A cell can only be used once per round. The game ends if there are no open slots left to fill,
or if a playable entity manages to get three of their own tokens in a row.

#### The Status Bar
You can find the status bar towards the bottom of the application. It will show you the current 
status of the game. This can be one of the following: `Game hasn't started`, `Board Reset!`,
`Started the Game!`, `%c wins!`, `Draw`, `Game in progress, %c's turn`.

#### The Sidebar
The sidebar is the red bar on the right side of the central app content, and has three useful buttons.
These are an exit button, a minimize button, and a help button. The exit button closes the app, while the
minimize button hides it, and the help button displays a simplified version of this ```README ```file.

***

### Bots
This implementation of Tic Tac Toe allows a user to play against bots that have varied levels of 
complexity.

#### Easy Bot
The easy bot, as the name suggests, is a mild opponent that can only make random placements.

#### Medium Bot
The medium bot is a slightly more challenging easy bot. If it detects that it has two tiles in a row 
and can win in a single move, then it will do exactly that. If it detects that the opponent has two 
tiles in a row and can win in a single move, then it will block that attempt. Otherwise, if none of
those conditions are met, then the bot will make a random placement.

#### Hard Bot
The hard bot is the most advanced of these bots, and it makes decisions by means of the 
[Minimax Algorithm]. It will consider every single possible outcome from its moves, and 
plot a move accordingly. This implementation was heavily inspire by [Sebastian Lague's Video],
which gives excellent insight as to how decision trees work.

***

Initial Release: 10/31/2021 - @mrodz

<!-- Shortened Links -->
[Minimax Algorithm]: https://en.wikipedia.org/wiki/Minimax
[Sebastian Lague's Video]: https://www.youtube.com/watch?v=l-hh51ncgDI


