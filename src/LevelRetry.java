import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LevelRetry {
	public void render(Graphics g, int lives) {
		Font font1 = new Font("serif", Font.BOLD, 60);
		g.setFont(font1);
		g.setColor(Color.red);
		g.drawString("Oopsies, life down", (Game.WIDTH/2)-215, 100);
		int newlives = lives+1;
		
		Font font2 = new Font("serif", Font.BOLD, 40);
		g.setFont(font2);		
		if (lives == 0) {
			g.drawString("Lives Remaining: " + newlives, Game.WIDTH/2-150, Game.HEIGHT/2);
			g.drawString("Last life, best use caution", Game.WIDTH/2-200, Game.HEIGHT/2+50);
			g.drawString("Press Space to continue", Game.WIDTH/2-180, Game.HEIGHT/2+150);
		} else {
			g.drawString("Lives Remaining: " + newlives, Game.WIDTH/2-150, Game.HEIGHT/2);
			g.drawString("Use them wisely...", Game.WIDTH/2-140, Game.HEIGHT/2+50);
			g.drawString("Press Space to continue", Game.WIDTH/2-180, Game.HEIGHT/2+90);
		}
	}
}