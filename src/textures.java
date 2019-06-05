import java.awt.image.BufferedImage;

public class textures {//holds all textures from sprite sheets and paddle
	private SpriteSheet ss, playerPaddle, bs, bh, pb, powers;
	public BufferedImage paddle, red, orange, yellow, limeGreen, neonGreen, green, cyan, lightBlue, darkBlue, purple, indigo, pink;
	public BufferedImage paddleP, redP, orangeP, yellowP, limeGreenP, neonGreenP, greenP, cyanP, lightBlueP, darkBlueP, purpleP, indigoP, pinkP;
	public BufferedImage start, exit, help, options, log, back;
	public BufferedImage startH, exitH, helpH, optionsH, logH, backH;
	public BufferedImage resume, menu, restart;
	
	public textures(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		playerPaddle = new SpriteSheet(game.getPaddle());
		bs = new SpriteSheet(game.getButtons());
		bh = new SpriteSheet(game.getHoverButtons());
		pb = new SpriteSheet(game.getPauseButtons());
		powers = new SpriteSheet(game.getPowerUpBricks());
		
		getTextures();
	}
	
	private void getTextures() {//(row, col)
		paddle = playerPaddle.grabImage(1, 1, 100, 30);
		
		//bricks
		red = ss.grabBrickImg(1, 1);
		orange = ss.grabBrickImg(1, 2);
		yellow = ss.grabBrickImg(1, 3);
		limeGreen = ss.grabBrickImg(1, 4);
		neonGreen = ss.grabBrickImg(1, 5);
		green = ss.grabBrickImg(1, 6);
		cyan = ss.grabBrickImg(2, 1);
		lightBlue = ss.grabBrickImg(2, 2);
		darkBlue = ss.grabBrickImg(2, 3);
		purple = ss.grabBrickImg(2, 4);
		indigo = ss.grabBrickImg(2, 5);
		pink = ss.grabBrickImg(2, 6);
		
		//power ups
		redP = powers.grabBrickImg(1, 1);
		orangeP = powers.grabBrickImg(1, 2);
		yellowP = powers.grabBrickImg(1, 3);
		limeGreenP = powers.grabBrickImg(1, 4);
		neonGreenP = powers.grabBrickImg(1, 5);
		greenP = powers.grabBrickImg(1, 6);
		cyanP = powers.grabBrickImg(2, 1);
		lightBlueP = powers.grabBrickImg(2, 2);
		darkBlueP = powers.grabBrickImg(2, 3);
		purpleP = powers.grabBrickImg(2, 4);
		indigoP = powers.grabBrickImg(2, 5);
		pinkP = powers.grabBrickImg(2, 6);
		
		//non-hover buttons
		start = bs.grabButtonImg(2,1);
		exit = bs.grabButtonImg(1,2);
		options = bs.grabButtonImg(2,3);
		help = bs.grabButtonImg(2,2);
		back = bs.grabButtonImg(1,3);
		log = bs.grabButtonImg(1,1);
		
		//hover buttons
		startH = bh.grabButtonHoverImg(2,1);
		exitH = bh.grabButtonHoverImg(1,2);
		optionsH = bh.grabButtonHoverImg(2,3);
		helpH = bh.grabButtonHoverImg(2,2);
		backH = bh.grabButtonHoverImg(1,3);
		logH = bh.grabButtonHoverImg(1,1);
		
		//pause menu buttons
		resume = pb.grabButtonImg(1,1);
		restart  = pb.grabButtonImg(1,2);
		menu  = pb.grabButtonImg(1,3);
	}
}