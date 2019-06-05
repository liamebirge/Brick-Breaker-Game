import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class NextLevel {
	public void render(Graphics g, int lives, int score, int level, int bonus, int prevScore) {
		Font font1 = new Font("serif", Font.BOLD, 60);
		Font font2 = new Font("serif", Font.BOLD, 40);
		Font font3 = new Font("serif", Font.BOLD, 32);
		Font font4 = new Font("serif", Font.BOLD, 29);
		g.setFont(font1);
		g.setColor(Color.red);
		g.drawString("Great Job!", (Game.WIDTH/2)-130, 100);
		
		if (score > 1000) {
			g.setFont(font4);
		} else {
			g.setFont(font3);
		}
		g.drawString("Score: " + bonus + " Level bonus + " + prevScore + " level score = " + score, Game.WIDTH/2-300, Game.HEIGHT/2-60);
		g.setFont(font2);
		g.drawString("Lives Remaining: " + (lives+1), Game.WIDTH/2-150, Game.HEIGHT/2);
		g.drawString("Use them wisely...", Game.WIDTH/2-140, Game.HEIGHT/2+50);
		g.drawString("Press Space to continue to level " + level, Game.WIDTH/2-300, Game.HEIGHT/2+150);
	}
}