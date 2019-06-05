import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/* 
 * upgrade ball speed as level counter increases
 * menu hover animations, cursor changes to pointer
 * fix collisions with ball and paddle
 * volume slider in options
 */

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 700;
	public static final int HEIGHT = 600;
	public static final int SCALE = 2;
	public final String title = "Brick Breaker";
	
	public int score = 0;//initial score of 0
	public int rows = 3, columns = 4;//starting rows and column count
	public int totalBricks = rows*columns;//sets amount of bricks based on # of rows and columns
	public int numBalls = 0;
	private boolean restart = false;
	public int ballposX = 350;//starting X position of ball
	public int ballposY = 500;//starting Y position of ball
	Random rand = new Random();//random
	public int lives = 2;
	public int levelCount = 2;
	public int stopper = 0;
	public int numDeaths = 0;
	private boolean scoreCalculated = false;
	private int bonus = 0;//level bonus
	private int prevScore = 0;
	private long startGameTime, startLevelTime, endGameTime, endLevelTime;
	private boolean timeCalc = true;
	
	private boolean running = true;
	private Thread thread;
	
	//images rendered directly onto the frame
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage paddle = null;
	private BufferedImage pauseBttn = null;
	private BufferedImage background = null;
	private BufferedImage spriteSheet = null;
	private BufferedImage buttonSheet = null;
	private BufferedImage buttonHoverSheet = null;
	private BufferedImage pauseButtons = null;
	private BufferedImage powerUpBricks = null;
	
	//classes
	private PlayerPaddle p;
	private Ball ball;
	private textures tex;
	public Bricks bricks;//map bricks
	
	//states
	private static Menu menu;
	private Help help;
	private Options options;
	private NextLevel nextLevel;
	private GameOver gameOver;
	private LevelRetry levelRetry;
	private Pause pause;
	private GameComplete gameDone;
	
	public static enum STATE{
		MENU,
		GAME, 
		OPTIONS,
		HELP,
		EXIT, 
		NEXTLEVEL,
		GAMEOVER,
		LEVELRETRY,
		PAUSE,
		RESTARTED,
		GAMECOMPLETE
	};
	
	public static STATE State = STATE.MENU;//initial state is the menu
	
	private LinkedList<Ball> b = new LinkedList<Ball>();//makes list of all active balls
	
	public void init() {
		this.transferFocus();
		
		startGameTime = System.currentTimeMillis();
		
		//initiate img loader and all frame images
		imgLoader loader = new imgLoader();
		paddle = loader.loadImage("/paddle.png");//import paddle graphic
		pauseBttn = loader.loadImage("/PauseButton.png");
		background = loader.loadImage("/Background.png");//import background graphic
		spriteSheet = loader.loadImage("/SpriteSheet.png");
		buttonSheet = loader.loadImage("/Buttons.png");
		buttonHoverSheet = loader.loadImage("/ButtonsHover.png");
		pauseButtons = loader.loadImage("/PauseMenu.png");
		powerUpBricks = loader.loadImage("/PowerUps.png");
		
		//key and mouse listeners
		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());
		
		//initialize the classes and states
		tex = new textures(this);
		p = new PlayerPaddle(WIDTH/2, 550, tex);//sets initial position of the paddle

		bricks = new Bricks(rows, columns, tex);//re-map the brick position
		
		menu = new Menu();
		help = new Help();
		options = new Options();
		nextLevel = new NextLevel();
		gameOver = new GameOver();
		levelRetry = new LevelRetry();
		pause = new Pause();
		gameDone = new GameComplete();
	}
	
	private synchronized void start() {//start game
		if (running) {
			run();
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {//stop game
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {//calculates the tick and fps of the program based on how long the render and tick function takes
		init();
		long lastTime = System.nanoTime();
		final double numberTicks = 60.0;
		double ns = 1000000000 / numberTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running) {//game loop
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / ns;
			lastTime = nowTime;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	private void tick() {//loops and updates the objects
		if (State == STATE.GAME) {//during the game state
			p.tick();//update the paddle
			//ball collisions with the boarders of the frame
			for (int i = 0; i < b.size(); i++) {//runs through list of balls
				ball = b.get(i);
				if(ball.getY() < 0) {//if the ball hits top boarder
					ball.setY(-ball.ballYdir);//reverse Y
				} else if (ball.getX() < 1) {//if ball hits left boarder
					ball.setX(-ball.ballXdir);//reverse X
				} else if (ball.getX() > WIDTH-20) {//if ball hits right boarder 
					ball.setX(-ball.ballXdir);//reverse X
				} else if (ball.getY() > HEIGHT || totalBricks <= 0) {//if ball exits bottom
					removeBall(ball);//remove the ball from the list
					numBalls--;//decrease number of balls
					//game over or lose life
				}
				
				//ball collisions with the paddle
				if((ball.getX() >= p.getX()-15 && ball.getX() < p.getX()+15) && ball.getY() >= p.getY()-20) {
					ball.setY(-ball.ballYdir);//reverse Y direction of ball
					ball.setX(ball.ballXdir-2 > 3 ? 3 : ball.ballXdir-2 < -3 ? -3 : ball.ballXdir-2);//decrease rightward momentum significantly while staying within 3 and -3
				}else if ((ball.getX() >= p.getX()+15 && ball.getX() < p.getX()+50) && ball.getY() >= p.getY()-20) {//if the ball collides with inner left side of paddle
					ball.setY(-ball.ballYdir);//reverse Y direction of ball
					ball.setX(ball.ballXdir-1 > 3 ? 3 : ball.ballXdir-1 < -3 ? -3 : ball.ballXdir-1);//decrease rightward momentum minimally while staying within 3 and -3
				}else if ((ball.getX() >= p.getX()+50 && ball.getX() < p.getX()+85) && ball.getY() >= p.getY()-20) {//if the ball collides with inner right side of paddle
					ball.setY(-ball.ballYdir);//reverse Y direction of ball
					ball.setX(ball.ballXdir+1 > 3 ? 3 : ball.ballXdir+1 < -3 ? -3 : ball.ballXdir+1);//decrease leftward momentum minimally while staying within 3 and -3
				}else if ((ball.getX() >= p.getX()+85 && ball.getX() < p.getX()+p.getWidth()) && ball.getY() >= p.getY()-20) {//if the ball collides with outer right side of paddle
					ball.setY(-ball.ballYdir);//reverse Y direction of ball
					ball.setX(ball.ballXdir+2 > 3 ? 3 : ball.ballXdir+2 < -3 ? -3 : ball.ballXdir+2);//significantly decrease leftward momentum while staying within 3 and -3
				}
				
				NEXT_FRAME: 
				for(int h = 0; h < bricks.map.length; h++) {
					for(int w = 0; w < bricks.map[0].length; w++) {//loop through all bricks
						if (bricks.map[h][w] > 0) {//if the brick exists
							//calculate where each brick is positioned
							int brickX = bricks.bX*w+50;
							int brickY = bricks.bY*h+50;
							//define the height and width of the bricks based on their scaled size
							int brickWidth = bricks.scaleWidth;
							int brickHeight = bricks.scaleHeight;
							
							//make hit-boxes for the brick and the ball
							Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), 20, 20);
							if(ballRect.intersects(brickRect)) {//if there is a collision with the ball and a brick
								if (bricks.map[h][w] == 2) {
									addBall(new Ball(brickX+(brickWidth/2), brickY+(brickHeight/2), this));
									numBalls++;
								}
								
								bricks.setBrickValue(0, h, w);//delete the brick
								totalBricks--;//remove the brick from the overall brick count
								score += (5 * (-h+rows));//increment score based on which row of block was destroyed and how many total rows there are
								
								//if the ball comes in contact with the brick, bounce the ball off in direction matching which side of the brick it collided with
								if (ball.getX() + 10 <= brickX /*hitting left side*/ || ball.getX() + 10 >= brickX + brickWidth /*hitting right side*/) {
									ball.setX(-(ball.ballXdir));//reverse x direction
								} else if (ball.getY() + 10 <= brickY /*hitting top*/ || ball.getY() + 10 >= brickY + brickHeight/*hitting bottom*/){
									ball.setY(-(ball.ballYdir));//reverse y direction
								} else {
									System.out.println("Unknown collision: " + ball.getX() + ":" + ball.getY());
								}
								break NEXT_FRAME;//end the loop, the ball has collided and must now move
							}
						}
					}
				}
				ball.tick();//update the ball
			}
		}
	}
	public void addBall(Ball ball) {//add a ball function
		b.add(ball);
	}
	public void removeBall(Ball ball) {//remove the ball
		b.remove(ball);
	}
	
	private boolean startGame = false;//for time calculations
	private void render() {//displays the graphics of the objects
		BufferStrategy buff = this.getBufferStrategy();
		if (buff == null) {
			createBufferStrategy(4);//make buffers
			return;
		}
		Graphics g = buff.getDrawGraphics();//draw buffers
		/////////////////////////////////////////////////////////////////////////
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0, 0, null);//draw background
		if (State == STATE.GAME) {
			if (startGame == false) {
				startLevelTime = System.currentTimeMillis();
				startGame = true;
			}
			g.drawImage(pauseBttn, 5, 5, null);//draw pause button
			p.render(g);//render player
			for (int i = 0; i < b.size(); i++) {//runs through list of balls and renders each
				ball = b.get(i);
				ball.render(g);
				
				if ((ball.getY() > HEIGHT) && numBalls <= 1) {//if ball exits bottom of screen
					int newLives = 0;//lives storage variable
					stopper++;//increments every time the draw function is looped
					if (stopper == 1) {//if the draw function has looped only once
						newLives = lives - 1;//decrement the number of lives by one
					}else {
						newLives = lives;//otherwise just keep the lives the same
						//without these conditions, the number of lives would decrease every time the render function is run 
					}
					if (lives > 0) {//if there are still lives left
						lives = newLives;//subtract one life
						State = STATE.LEVELRETRY;
					} else if (lives == 0) {//if there are no lives left
						//notify that the game has ended and display score
						restart = true;
						State = STATE.GAMEOVER;
					}
				}
			}
			bricks.render((Graphics2D)g);//display brick map
			
			//score board
			g.setColor(Color.yellow);
			//g.setFont(newFont(font, style, size));
			g.setFont(new Font("serif", Font.BOLD, 25));//25pt bold serif font
			//g.drawString(String, x, y);
			g.drawString(score + "pts", 590, 30);
			
			if (totalBricks <= 0) {//if there are no bricks left (game complete)
				//display score and prompt to replay
				State = STATE.NEXTLEVEL;
			}
			
			//displays lives as dots in center of the screen. second condition in if statement is to account for
			//	bug where after restart the number of lives does not decrease after first death
			if ((!restart && lives == 2) || (restart && lives == 1 && numDeaths == 0)) {
				g.setColor(Color.yellow);
				g.fillOval(WIDTH/2-20, 15, 10, 10);
				g.fillOval(WIDTH/2-5, 15, 10, 10);
				g.fillOval(WIDTH/2+10, 15, 10, 10);
			} else if ((!restart && lives == 1) || (restart && numDeaths == 1)) {
				g.setColor(Color.yellow);
				g.fillOval(WIDTH/2-13, 15, 10, 10);
				g.fillOval(WIDTH/2+2, 15, 10, 10);
			} else if ((!restart && lives == 0) || (restart && numDeaths == 2)) {
				g.setColor(Color.yellow);
				g.fillOval(WIDTH/2-5, 15, 10, 10);
			}
		} else if (State == STATE.MENU) {//render menu
			menu.render(g, tex, this);
		} else if (State == STATE.OPTIONS) {//render options
			options.render(g, tex);
		} else if (State == STATE.HELP) {//render help
			help.render(g, tex);
		} else if (State == STATE.NEXTLEVEL) {//render next level
			if (!scoreCalculated) {//calculate the new score with bonus after the level is completed
				bonus = 50*levelCount;//level bonus
				prevScore = score;
				score = score + bonus;//level bonus added to score
				scoreCalculated = true;
			}
			endLevelTime = System.currentTimeMillis();
			nextLevel.render(g, lives , score, levelCount , bonus, prevScore);//pass all variables to the render function for display
		} else if (State == STATE.GAMEOVER) {//render game over
			if (timeCalc) {
				endGameTime = System.currentTimeMillis();
				endLevelTime = System.currentTimeMillis();
				timeCalc = false;
			}
			gameOver.render(g, score, startGameTime, endGameTime);
		} else if (State == STATE.LEVELRETRY) {//render retry
			levelRetry.render(g, lives);
		} else if (State == STATE.PAUSE) {//render pause menu
			pause.render(g, tex);
		} else if (State == STATE.RESTARTED) {//render restart screen
			gameOver();
			State = STATE.GAME;
		}  else if (State == STATE.GAMECOMPLETE) {//render restart screen
			if (timeCalc) {
				endGameTime = System.currentTimeMillis();
				endLevelTime = System.currentTimeMillis();
				timeCalc = false;
			}
			gameDone.render(g, score, lives, startLevelTime, endLevelTime, startGameTime, endGameTime);
		}
		
		if (columns >= 20) {
			State = STATE.GAMECOMPLETE;
		}
		/////////////////////////////////////////////////////////////////////////
		//g.dispose();
		buff.show();
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (State == STATE.GAME || State == STATE.PAUSE) {//during game only
			if(key == KeyEvent.VK_RIGHT) {
				p.setVelocityX(5);
			} else if(key == KeyEvent.VK_LEFT) {
				p.setVelocityX(-5);
			} else if(key == KeyEvent.VK_SPACE) {
				if(numBalls < 1) {//if the game is not started, launch ball on space
					addBall(new Ball((int)p.getX()+50, (int)p.getY()-20, this));
					numBalls++;
				}
			} else if (key == KeyEvent.VK_ENTER) {//little cheaty trick
				//if (columns < 20) {//not too crazy champ
					rows++;//increase number of rows and columns by 1
					columns++;
					totalBricks = rows*columns;//reset brick count
					
					bricks = new Bricks(rows, columns, tex);//re-map the brick position
				//}
			} else if (key == KeyEvent.VK_ESCAPE) {
				if (State == STATE.PAUSE) {//if the game is paused
					State = STATE.GAME;//resume game on ESC press
				} else {
					State = STATE.PAUSE;//otherwise display pause menu
				}
			}
		} else if (State == STATE.NEXTLEVEL || State == STATE.LEVELRETRY || State == STATE.GAMEOVER) {//only during transition screens
			if(key == KeyEvent.VK_SPACE) {
				if (State == STATE.NEXTLEVEL) {//last round won and on to next level
					restart = false;
					for (int i = 0; i < b.size(); i++) {//runs through list of balls
						ball = b.get(i);
						ball.placeBall(WIDTH/2, 500);//reset the ball position
						ball.firstLaunch = true;
						ball.tick();//launch ball
					}
					p.setX(WIDTH/2);//reset the paddle's position
					rows++;//increase number of rows and columns by 1
					columns++;
					levelCount++;//increase level
					totalBricks = rows*columns;//reset brick count
					
					bricks = new Bricks(rows, columns, tex);//re-map the brick position
					
					scoreCalculated = false;
					startLevelTime = System.currentTimeMillis();
				} else if(State == STATE.LEVELRETRY) {//ball has gone bellow the bottom boarder
					if (restart) {//for restarted games
						numDeaths++;
					}
					stopper = 0;
					for (int i = 0; i < b.size(); i++) {//runs through list of balls
						ball = b.get(i);
						ball.placeBall(WIDTH/2, 500);//reset the ball position
						ball.firstLaunch = true;
						ball.tick();//launch ball
					}
					p.setX(WIDTH/2);//reset the paddle's position
				} else if(State == STATE.GAMEOVER) {
					gameOver();
				}
				State = STATE.GAME;
			}
		}
		if (State == STATE.GAMECOMPLETE) {
			if(key == KeyEvent.VK_SPACE) {
				State = STATE.GAME;
				gameOver();
			}else if (key == KeyEvent.VK_ESCAPE) {
				State = STATE.MENU;
				gameOver();
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) {//stop moving paddle on key release
			p.setVelocityX(0);
		} else if(key == KeyEvent.VK_LEFT) {
			p.setVelocityX(0);
		}
	}
	public void gameOver() {
		for (int i = 0; i < b.size(); i++) {//runs through list of balls
			ball = b.get(i);
			ball.placeBall(WIDTH/2, 500);//reset the ball position
			ball.firstLaunch = true;
			ball.tick();//launch ball
		}
		timeCalc = true;
		p.setX(WIDTH/2);//reset the paddle's position
		rows = 3;//reset number of rows and columns
		columns = 4;
		levelCount = 2;//reset level number
		score = 0;//reset score
		numDeaths = 0;//reset number of deaths
		if (restart) {//if the game was restarted set number of lives to one to account for oddity
			lives = 1;
		} else {
			lives = 2;
		}
		totalBricks = rows*columns;//reset brick count
		
		bricks = new Bricks(rows, columns, tex);//re-map the brick position
		
		startGameTime = System.currentTimeMillis();
		startLevelTime = System.currentTimeMillis();
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		ImageIcon img = new ImageIcon("C:/Users//Liam/JavaCode/Brick-Breaker/res/Breaker.png");//icon file
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JFrame frame = new JFrame(game.title);
		frame.add(game);
		frame.pack();
		frame.setIconImage(img.getImage());//display icon
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stop the program when exiting
		frame.setResizable(false);//not resizable
		frame.setLocationRelativeTo(null);//starts in center of screen
		frame.setVisible(true);//will be visible
		frame.setAlwaysOnTop(true);//will remain above all other programs while running
		game.start();//start thread of the game
	}
	
	//getters for buffered images and menu instance
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	public BufferedImage getPaddle() {
		return paddle;
	}
	public BufferedImage getButtons() {
		return buttonSheet;
	}
	public BufferedImage getHoverButtons() {
		return buttonHoverSheet;
	}
	public BufferedImage getPauseButtons() {
		return pauseButtons;
	}
	public BufferedImage getPowerUpBricks() {
		return powerUpBricks;
	}
	public static Menu getMenu() {
		return menu;
	}
}