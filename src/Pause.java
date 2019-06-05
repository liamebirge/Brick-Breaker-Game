import java.awt.*;

public class Pause {
	textures tex;
	
	public void render(Graphics g, textures tex) {
		this.tex = tex;
		
		Font font1 = new Font("serif", Font.BOLD, 70);
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Paused", (Game.WIDTH/2)-90, 100);
		
		g.drawImage(tex.resume, Game.WIDTH/2-80, 150, null);
		g.drawImage(tex.restart, Game.WIDTH/2-80, 250, null);
		g.drawImage(tex.menu, Game.WIDTH/2-80, 350, null);
		g.drawImage(tex.exit, Game.WIDTH/2-80, 450, null);
	}
}