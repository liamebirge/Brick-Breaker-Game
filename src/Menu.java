import java.awt.*;

public class Menu{
	textures tex;
	MouseInput mouse = new MouseInput();
	public static boolean playH = false;
	public boolean optionsH = false;
	public boolean helpH = false;
	public boolean exitH = false;
	boolean firstRender = true;

	public void render(Graphics g, textures tex, Game game) {
		this.tex = tex;
		//opening theme sound
		if (firstRender ) {
			//mouse.music("/Musics/OpeningTheme.wav");
		}
		firstRender = false;
		
		//title
		Font font1 = new Font("serif", Font.BOLD, 70);
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Brick Breaker", (Game.WIDTH/2)-200, 100);
		
		//buttons
		if (playH) {
			g.drawImage(tex.startH, Game.WIDTH/2-80, 150, null);
		} else {
			g.drawImage(tex.start, Game.WIDTH/2-80, 150, null);
		}
		if (optionsH) {
			g.drawImage(tex.optionsH, Game.WIDTH/2-80, 250, null);
		} else {
			g.drawImage(tex.options, Game.WIDTH/2-80, 250, null);
		}
		if (helpH) {
			g.drawImage(tex.helpH, Game.WIDTH/2-80, 350, null);
		} else {
			g.drawImage(tex.help, Game.WIDTH/2-80, 350, null);
		}
		if (exitH) {
			g.drawImage(tex.exitH, Game.WIDTH/2-80, 450, null);
		} else {
			g.drawImage(tex.exit, Game.WIDTH/2-80, 450, null);
		}
		
	}
	
	public Menu getMenu() {
		return this;
	}
}