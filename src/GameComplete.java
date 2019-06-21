import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameComplete {	
	public void render(Graphics g, int score, int lives, long startLevelTime, long endLevelTime, long startGameTime, long endGameTime) {
		long gameTime = (endGameTime - startGameTime)/1000;
		long levelTime = (endLevelTime - startLevelTime)/1000;
		long gameMins = gameTime/60;
		long gameSecs = gameTime%60;
		long levelMins = levelTime/60;
		long levelSecs = levelTime%60;
		
		Font font1 = new Font("serif", Font.BOLD, 50);
		Font font2 = new Font("serif", Font.BOLD, 32);
		g.setFont(font1);
		g.setColor(Color.red);
		g.drawString("Great job, you beat the game!", 35, 100);
		
		g.setFont(font2);
		g.drawString("Final Score: " + score, 50, Game.HEIGHT/2-60);
		g.drawString("Remaining Lives: " + (lives+1), 50, Game.HEIGHT/2);
		g.drawString("Game Completed in " + gameMins + " minutes and " + gameSecs + " seconds", 15, Game.HEIGHT/2+30);
		g.drawString("Level Completed in " + levelMins + " minutes and " + levelSecs + " seconds", 15, Game.HEIGHT/2+60);
		g.drawString("Press Space to restart and play again", 50, Game.HEIGHT/2+130);
		g.drawString("Press ESC to return to main menu", 50, Game.HEIGHT/2+160);
	}
}
