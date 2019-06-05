import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver {
	public void render(Graphics g, int score, long gameStartTime, long gameEndTime) {
		long gameTime = (gameEndTime - gameStartTime)/1000;
		
		Font font1 = new Font("serif", Font.BOLD, 60);
		Font font2 = new Font("serif", Font.BOLD, 70);
		Font font3 = new Font("serif", Font.BOLD, 50);
		
		g.setFont(font1);
		g.setColor(Color.red);
		g.drawString("Game Over", (Game.WIDTH/2)-145, 100);
		
		g.setFont(font2);
		g.setColor(Color.yellow);
		g.drawString("YOU FOOL", Game.WIDTH/2-180, Game.HEIGHT/2-100);
		g.setFont(font3);
		g.setColor(Color.red);
		g.drawString("Final score: " + score, Game.WIDTH/2-130, Game.HEIGHT/2);
		g.drawString("It took you " + gameTime + " seconds to die this game", 60, Game.HEIGHT/2+30);
		g.drawString("Press space to restart", Game.WIDTH/2-220, Game.HEIGHT/2+100);
	}
}