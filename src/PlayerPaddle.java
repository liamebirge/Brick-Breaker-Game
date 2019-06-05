import java.awt.Graphics;

public class PlayerPaddle {
	private double x;
	private double y;
	
	private double velocityX = 0;
	
	private textures tex;
	
	public PlayerPaddle(double x, double y, textures tex) {
		this.x = x;//sets initial x and y coordinates of player
		this.y = y;
		this.tex = tex;
	}
	public void tick() {//movement of player
		x += velocityX;
		y = 550;//fixed y
		
		//border collisions
		if (x <= 0) x = 0;
		if (x >= 600) x = 600;
	}
	public void render(Graphics g) {
		g.drawImage(tex.paddle, (int)x, (int)y, null);
	}
	
	//getters and setters for x, y, and velocity
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	public double getWidth() {
		return tex.paddle.getWidth();
	}
}