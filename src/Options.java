import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Options {	
	public void render(Graphics g, textures tex) {
		Graphics2D g2d = (Graphics2D) g;
		
		//title
		Font font1 = new Font("serif", Font.BOLD, 60);
		Font font2 = new Font("serif", Font.BOLD, 30);
		Font font3 = new Font("serif", Font.BOLD, 33);
		Font font4 = new Font("serif", Font.BOLD, 20);
		
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Options", (Game.WIDTH/2)-70, 100);
		
		//back button
		g.drawImage(tex.back, 10, 30, null);
		
		//Music selection
		g.setFont(font2);
		//g.drawString("Coming soon", 20, 200);
		g.drawString("Music", 20, 200);
		
		g.setFont(font4);
		g2d.drawRect(570, 50, 100, 40);
		g.drawString("Stop music", 577, 75);
		
		g.setFont(font3);
		g2d.drawRect(20, 220, 300, 60);
		g.drawString("PLOK! Boss Theme", 28, 260);
		
		g2d.drawRect(20, 290, 300, 60);
		g.drawString("Cybernoid (C64)", 50, 330);
		
		g.setFont(font2);
		g2d.drawRect(20, 360, 300, 60);
		g.drawString("Duck Tales-The Moon", 30, 400);
		
		g.setFont(font3);
		g2d.drawRect(20, 430, 300, 60);
		g.drawString("Hyrule Castle", 70, 470);
		
		g2d.drawRect(20, 500, 300, 60);
		g.drawString("Spanky's Quest", 70, 540);
		
		g.setFont(font2);
		g2d.drawRect(Game.WIDTH-320, 220, 300, 60);
		g.drawString("SSF2 Guile's Theme", Game.WIDTH-295, 260);
	}
}