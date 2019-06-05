import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int row, int col, int width, int height) {//grabs paddle image
		BufferedImage img = image.getSubimage((row*100) - 100, (col*30) - 30, width, height);
		return img;
	}
	//grabImage(1, 1, 32, 32) gets image in first col and first row
	public BufferedImage grabBrickImg(int row, int col) {//grabs bricks from brick sprite sheet
		BufferedImage img = image.getSubimage((col*100) - 100, (row*60) - 60, 100, 60);
		return img;
	}
	
	public BufferedImage grabButtonImg(int row, int col) {//grabs non-hover buttons from button sprite sheet
		BufferedImage img = image.getSubimage((col*200) - 200, (row*80) - 80, 200, 80);
		return img;
	}
	public BufferedImage grabButtonHoverImg(int row, int col) {//grabs hover buttons from sprite sheet
		BufferedImage img = image.getSubimage((col*200) - 200, (row*80) - 80, 200, 80);
		return img;
	}
}