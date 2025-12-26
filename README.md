# Breakout
This project creates the iconic 1976 video game Breakout developed by Atari Inc.
The game begins with a set of rows of bricks a paddle and a ball. The player must break all the bricks displayed on the screen using a single ball to win the game. Each brick is worth one point. If the ball misses the paddle's rebound, the player's life is decremented, but the amount of bricks remains the same. If the user runs out of lives, the game is over.

### How to Run the Program
This project was developed using Intellij IDEA 2025.2.1. It is recommended that the user should this software if they want to play the game.

1. Clone the repo in your own local repository
2. Open the project in Intellij IDEA
3. Make sure that you are using JDK 17.0.16+
4. Run the main program located src/main/java/org.example/Main

### How to Play
The ball spawns above the paddle with enough space to allow the user enough time to rebound the ball.

1. The paddle moves only horizontally, use the 'A' and 'D' keys to move left and right respectively to rebound the ball.
2. When the game is over (i.e. the user has won or lost) press the 'R' key to restart the game. This will reset your lives and score as well as the brick layout of the game.

### Future Improvements
This implementation of Breakout can be improved in many ways
* Instead of one round, there can be multiple rounds of increasing difficulty
* Implement different colours for different rows of bricks to indicate the number of points that the brick is worth (ex. Red = 3, Green = 2, Blue = 1, etc.)
* Increase the speed of the ball as the number of points increases to increase difficulty.

Below is a sample of the gameplay, I hope you enjoy it!<br><br>
![Image](https://github.com/user-attachments/assets/90b11a0f-10be-441a-beca-f074db6142b2)

   
