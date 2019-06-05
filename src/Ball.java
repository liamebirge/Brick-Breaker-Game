import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Ball {
	private int x;
	private int y;
	private int scale = 0;//scale of ball's color change
	private int red = 1, green = 1, blue = 1;//initial RGB values
	
	Random rand = new Random();//random
	public int ballXdir;
	public int ballYdir;
	private int speeds[] = {3, 2, -2, -3};//all possible speeds/directions for x and y
	private int startSpeedsY[] = {-2, -3};//starting y speeds since the ball needs to start by moving upwards
	
	public boolean firstLaunch = true;
	
	public Ball (int x, int y, Game game) {
		this.x = x;
		this.y = y;
	}
	public void tick() {		
		if(firstLaunch) {//if the ball has not been launched before
			ballXdir = speeds[rand.nextInt(3)];//choose any of the possible speeds/directions for x
			ballYdir = startSpeedsY[rand.nextInt(1)];//choose the starting speed of the y direction
			firstLaunch = false;//no longer the first launch
		}
		
		//increment position based on the direction of the ball
		this.y += ballYdir;
		this.x += ballXdir;
	}
	
	public void render(Graphics g) {
		//color changing ball
		double frequency = 0.1, amplitude = 127, center = 128;
		if (scale < 200) {
			red = (int)(Math.sin(frequency*scale) * amplitude + center);
			green = (int)(Math.sin(frequency*scale + 2) * amplitude + center);
			blue = (int)(Math.sin(frequency*scale + 4) * amplitude + center);
			scale++;
		}else {
			scale = 0;
		}
		//Color(r, g, b);
		g.setColor(new Color(red, green, blue));
		g.fillOval(x, y, 20, 20);
	}
	
	//getters and setters for the x and y values
	public int getY() {
		return this.y;
	}
	public int getX() {
		return this.x;
	}
	public void setY(int yDir) {
		ballYdir = yDir;
	}
	public void setX(int xDir) {
		ballXdir = xDir;
	}
	
	//method to place the ball at specific coords
	public void placeBall(int x, int y) {
		this.x = x;
		this.y = y;
	}
}