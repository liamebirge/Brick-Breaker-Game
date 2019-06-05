import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bricks {
	public int map[][];
	public int brickX;
	public int brickY;
	public int scaleWidth, scaleHeight, bX, bY;
	Random rand = new Random();
	private textures tex;
	private int row, col;
	private int totalBricks;
	BufferedImage resized;
	
	BufferedImage red, orange, yellow, limeGreen, neonGreen, green, cyan, lightBlue, darkBlue, purple, indigo, pink;
	BufferedImage redP, orangeP, yellowP, limeGreenP, neonGreenP, greenP, cyanP, lightBlueP, darkBlueP, purpleP, indigoP, pinkP;
	
	public Bricks (int row, int col, textures tex) {//generates a grid of bricks based on how many rows and columns are asked for
		map = new int[row][col];//creates 2D array based on specified row and column size
		this.tex = tex;//import texture instance from the game class
		this.row = row;
		this.col = col;
		totalBricks = row*col;
		
		//randomize placement of power up
		int doubleBallPlaceRow = rand.nextInt(row);
		int doubleBallPlaceCol = rand.nextInt(col);
		int doubleBallPlaceRow2 = rand.nextInt(row);
		int doubleBallPlaceCol2 = rand.nextInt(col);
		int doubleBallPlaceRow3 = rand.nextInt(row);
		int doubleBallPlaceCol3 = rand.nextInt(col);
		int doubleBallPlaceRow4 = rand.nextInt(row);
		int doubleBallPlaceCol4 = rand.nextInt(col);
		int doubleBallPlaceRow5 = rand.nextInt(row);
		int doubleBallPlaceCol5 = rand.nextInt(col);
		
		for(int i = 0; i < map.length; i++) {//makes brick presence true at each spot in the grid
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
				if (i == doubleBallPlaceRow && j == doubleBallPlaceCol) {
					if (totalBricks > 12) {//level higher than level 1
						map[i][j] = 2;//one power up
					}
				}
				if (i == doubleBallPlaceRow2 && j == doubleBallPlaceCol2) {
					if (totalBricks > 30) {//level higher than level 2
						map[i][j] = 2;//two power ups
					}
				}
				if (i == doubleBallPlaceRow3 && j == doubleBallPlaceCol3) {
					if (totalBricks > 56) {//level higher than level 5
						map[i][j] = 2;//three power ups
					}
				}
				if (i == doubleBallPlaceRow4 && j == doubleBallPlaceCol4) {
					if (totalBricks > 110) {//level higher than level 9
						map[i][j] = 2;//four power ups
					}
				}
				if (i == doubleBallPlaceRow5 && j == doubleBallPlaceCol5) {
					if (totalBricks > 240) {//level higher than level 14
						map[i][j] = 2;//five power ups
					}
				}
			}
		}
	}
	public void tick() {
		
	}
	private static int MAXx=(Game.WIDTH-50);
	private static int MINx=50;
	private static int MAXy=(Game.HEIGHT-350);
	private static int MINy=50;

	public int limitX(int xCoord) {
	    return (xCoord > MAXx) ? MAXx : (xCoord < MINx ? MINx: xCoord );
	}
	public int limitY(int yCoord) {
	    return (yCoord > MAXy) ? MAXy : (yCoord < MINy ? MINy: yCoord );
	}
	public void render(Graphics2D g) {
		//these methods occur outside of the for loops as resizing is quite process intensive and only needs to be calculated each
		//  render cycle and not for every last brick. this increases frame-rate when rendering more bricks
		//variables to calculate brick X and Y when detecting collisions
		bX = ((Game.WIDTH-100)/col);
		bY = ((Game.HEIGHT-400)/row);
		
		//determine brick image height and width based on how many columns or rows there are
		scaleWidth = (Game.WIDTH-100)/col;
		scaleHeight = (Game.HEIGHT-400)/row;
		
		//assign resized images to new buffered image
		red = imgLoader.resize(tex.red, scaleWidth, scaleHeight);
		orange = imgLoader.resize(tex.orange, scaleWidth, scaleHeight);
		yellow = imgLoader.resize(tex.yellow, scaleWidth, scaleHeight);
		limeGreen = imgLoader.resize(tex.limeGreen, scaleWidth, scaleHeight);
		neonGreen = imgLoader.resize(tex.neonGreen, scaleWidth, scaleHeight);
		green = imgLoader.resize(tex.green, scaleWidth, scaleHeight);
		cyan = imgLoader.resize(tex.cyan, scaleWidth, scaleHeight);
		lightBlue = imgLoader.resize(tex.lightBlue, scaleWidth, scaleHeight);
		darkBlue = imgLoader.resize(tex.darkBlue, scaleWidth, scaleHeight);
		purple = imgLoader.resize(tex.purple, scaleWidth, scaleHeight);
		indigo = imgLoader.resize(tex.indigo, scaleWidth, scaleHeight);
		pink = imgLoader.resize(tex.pink, scaleWidth, scaleHeight);
		
		//Power up resized images
		redP = imgLoader.resize(tex.redP, scaleWidth, scaleHeight);
		orangeP = imgLoader.resize(tex.orangeP, scaleWidth, scaleHeight);
		yellowP = imgLoader.resize(tex.yellowP, scaleWidth, scaleHeight);
		limeGreenP = imgLoader.resize(tex.limeGreenP, scaleWidth, scaleHeight);
		neonGreenP = imgLoader.resize(tex.neonGreenP, scaleWidth, scaleHeight);
		greenP = imgLoader.resize(tex.greenP, scaleWidth, scaleHeight);
		cyanP = imgLoader.resize(tex.cyanP, scaleWidth, scaleHeight);
		lightBlueP = imgLoader.resize(tex.lightBlueP, scaleWidth, scaleHeight);
		darkBlueP = imgLoader.resize(tex.darkBlueP, scaleWidth, scaleHeight);
		purpleP = imgLoader.resize(tex.purpleP, scaleWidth, scaleHeight);
		indigoP = imgLoader.resize(tex.indigoP, scaleWidth, scaleHeight);
		pinkP = imgLoader.resize(tex.pinkP, scaleWidth, scaleHeight);
		
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				
				//determine brick's X or Y position based on how many rows and columns there are and what brick is being looked at
				//add 50 pixel boarder around brick area
				//determine the width and height of brick spawn area
				brickX = ((Game.WIDTH-100)/col)*j+50;
				brickY = ((Game.HEIGHT-400)/row)*i+50;
				
				if (map[i][j] == 1) {//if there is a value of 1 in the brick array, create a brick there
					//for each row of bricks, display a different colored brick
					if (i == 0) {
						g.drawImage(red, brickX, brickY, null);
					} else if (i == 1 || i == 12) {
						g.drawImage(orange, brickX, brickY, null);
					} else if (i == 2 || i == 13) {
						g.drawImage(yellow, brickX, brickY, null);
					} else if (i == 3 || i == 14) {
						g.drawImage(limeGreen, brickX, brickY, null);
					} else if (i == 4 || i == 15) {
						g.drawImage(neonGreen, brickX, brickY, null);
					} else if (i == 5 || i == 16) {
						g.drawImage(green, brickX, brickY, null);
					} else if (i == 6 || i == 17) {
						g.drawImage(cyan, brickX, brickY, null);
					} else if (i == 7 || i == 18) {
						g.drawImage(lightBlue, brickX, brickY, null);
					} else if (i == 8 || i == 19) {
						g.drawImage(darkBlue, brickX, brickY, null);
					} else if (i == 9 || i == 20) {
						g.drawImage(purple, brickX, brickY, null);
					} else if (i == 10) {
						g.drawImage(indigo, brickX, brickY, null);
					} else if (i == 11) {
						g.drawImage(pink, brickX, brickY, null);
					}
				}
				if (map[i][j] == 2) {
					if (i == 0) {
						g.drawImage(redP, brickX, brickY, null);
					} else if (i == 1 || i == 12) {
						g.drawImage(orangeP, brickX, brickY, null);
					} else if (i == 2 || i == 13) {
						g.drawImage(yellowP, brickX, brickY, null);
					} else if (i == 3 || i == 14) {
						g.drawImage(limeGreenP, brickX, brickY, null);
					} else if (i == 4 || i == 15) {
						g.drawImage(neonGreenP, brickX, brickY, null);
					} else if (i == 5 || i == 16) {
						g.drawImage(greenP, brickX, brickY, null);
					} else if (i == 6 || i == 17) {
						g.drawImage(cyanP, brickX, brickY, null);
					} else if (i == 7 || i == 18) {
						g.drawImage(lightBlueP, brickX, brickY, null);
					} else if (i == 8 || i == 19) {
						g.drawImage(darkBlueP, brickX, brickY, null);
					} else if (i == 9 || i == 20) {
						g.drawImage(purpleP, brickX, brickY, null);
					} else if (i == 10) {
						g.drawImage(indigoP, brickX, brickY, null);
					} else if (i == 11) {
						g.drawImage(pinkP, brickX, brickY, null);
					}
				}
			}
		}
	}
	public void setBrickValue(int value, int row, int col) {//function to set specific brick to 0 when it gets destroyed
		map[row][col] = value;
	}
}