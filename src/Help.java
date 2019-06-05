import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Help {	
	public void render(Graphics g, textures tex) {
		//title
		Font font1 = new Font("serif", Font.BOLD, 60);
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Game Help", (Game.WIDTH/2)-90, 100);
		
		//back button
		g.drawImage(tex.back, 10, 30, null);
		
		//content
		g.setColor(Color.red);
		Font font2 = new Font("serif", Font.BOLD, 30);
		Font font3 = new Font("serif", Font.BOLD, 20);
		g.setFont(font2);
		g.drawString("Controls: ", 20, 200);
		g.setFont(font3);
		g.drawString("Left and right arrows move the paddle.", 20, 230);//30 px between sect. title and content
		g.drawString("Press space to launch a ball and to pass text-screens.", 20, 250);//20px between content
		g.drawString("Press the ESC button to return to menu (when not in game) or pause the game.", 20, 270);
		g.drawString("Pause the game with the pause button (top left) or with ESC.", 20, 290);
		
		g.setColor(Color.orange);
		g.setFont(font2);
		g.drawString("Goal: ", 20, 340);//50px between sections
		g.setFont(font3);
		g.drawString("Use the ball to break all the bricks to gain points and progress levels.", 20, 370);
		g.drawString("Try to get the highest score possible!", 20, 390);
		
		g.setColor(Color.green);
		g.setFont(font2);
		g.drawString("Tips: ", 20, 440);
		g.setFont(font3);
		g.drawString("Depending on the side of the paddle, the ball will bounce in different directions.", 20, 470);
		g.drawString("The ball will retrace its previous trajectory if bounced back at the same angle.", 20, 490);
		g.drawString("You can choose music from the options menu that will play in the background.", 20, 510);
		g.drawString("Songs can be changed/stopped midgame by pausing, hitting menu then options.", 20, 530);
	}
}