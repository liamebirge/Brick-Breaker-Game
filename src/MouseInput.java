import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MouseInput implements MouseListener{
	int tracksPlaying = 0;
	boolean stopMusic = false;
	boolean playH, helpH, optionsH, exitH;
	Clip clip;
	
	//play music function
	public void music(String path){
		if (tracksPlaying == 0) {
			try{
				//AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path)));
			    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
			    clip = AudioSystem.getClip();
			    clip.open(audioInputStream);
			    clip.start();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			tracksPlaying++;
		}
	}
	
	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.State == Game.STATE.MENU) {
			//play button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 150 && my <= 230) {
					Game.State = Game.STATE.GAME;
				}
			}
			
			//options button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 250 && my <= 330) {
					Game.State = Game.STATE.OPTIONS;
				}
			}
			//help button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 350 && my <= 430) {
					Game.State = Game.STATE.HELP;
				}
			}
			//exit button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 450 && my <= 530) {
					System.exit(1);
				}
			}
		} else if (Game.State == Game.STATE.HELP) {
			//back button
			if(mx >= 10 && mx <= 210) {
				if (my >= 30 && my <= 110) {
					Game.State = Game.STATE.MENU;
				}
			}
		} else if (Game.State == Game.STATE.OPTIONS) {
			
			//back button
			if(mx >= 10 && mx <= 210) {
				if (my >= 30 && my <= 110) {
					Game.State = Game.STATE.MENU;
				}
			}
			//stop music button
			if(mx >= 570 && mx <= 670) {
				if (my >= 50 && my <= 90) {
					clip.stop();
					tracksPlaying--;
				}
			}
			//PLOK!
			if(mx >= 20 && mx <= 320) {
				if (my >= 220 && my <= 280) {
					music("/Musics/Plok! Boss.wav");
				}
			}
			//Cybernoid
			if(mx >= 20 && mx <= 320) {
				if (my >= 290 && my <= 350) {
					music("/Musics/Cybernoid (C64).wav");
				}
			}
			//Duck Tales
			if(mx >= 20 && mx <= 320) {
				if (my >= 360 && my <= 420) {
					music("/Musics/DuckTales The Moon Theme.wav");
				}
			}
			//Hyrule Castle
			if(mx >= 20 && mx <= 320) {
				if (my >= 430 && my <= 490) {
					music("/Musics/Hyrule Castle - Link to the Past.wav");
				}
			}
			//Spanky
			if(mx >= 20 && mx <= 320) {
				if (my >= 500 && my <= 560) {
					music("/Musics/Spanky's Quest - City.wav");
				}
			}
			//super street fighter 2
			if(mx >= 380 && mx <= 680) {
				if (my >= 220 && my <= 280) {
					music("/Musics/Super Street Fighter II - Guile's Theme.wav");
				}
			}
			//g2d.drawRect(Game.WIDTH-320, 220, 300, 60);
		} else if (Game.State == Game.STATE.PAUSE) {	
			//Resume
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2+120) {
				if (my >= 150 && my <= 230) {
					Game.State = Game.STATE.GAME;
				}
			}
			//restart
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2+120) {
				if (my >= 250 && my <= 330) {
					Game.State = Game.STATE.RESTARTED;
				}
			}
			//menu
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2+120) {
				if (my >= 350 && my <= 430) {
					Game.State = Game.STATE.MENU;
				}
			}
			//Exit
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2+120) {
				if (my >= 450 && my <= 530) {
					System.exit(1);
				}
			}
		} else if (Game.State == Game.STATE.GAME) {
			//Pause button
			if(mx >= 5 && mx <= 35) {
				if (my >= 5 && my <= 35) {
					Game.State = Game.STATE.PAUSE;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		Point point = e.getPoint();
		
		if (Game.State == Game.STATE.MENU) {
			//play button
			if((point.getX() >= 270 && point.getX() <= 470) && (point.getY() >= 150 && point.getY() <= 230)) {
				Menu.playH = (true);
			}
			
			//options button
			if(point.getX() >= Game.WIDTH/2-80 && point.getX() <= Game.WIDTH/2 + 120) {
				if (point.getY() >= 250 && point.getY() <= 330) {
					optionsH = (true);
				}
			}
			//help button
			if(point.getX() >= Game.WIDTH/2-80 && point.getX() <= Game.WIDTH/2 + 120) {
				if (point.getY() >= 350 && point.getY() <= 430) {
					helpH = (true);
				}
			}
			//exit button
			if(point.getX() >= Game.WIDTH/2-80 && point.getX() <= Game.WIDTH/2 + 120) {
				if (point.getY() >= 450 && point.getY() <= 530) {
					exitH = (true);
				}
			}
		} else if (Game.State == Game.STATE.HELP) {
			//back button
			if(point.getX() >= 10 && point.getX() <= 210) {
				if (point.getY() >= 30 && point.getY() <= 110) {
				}
			}
		} else if (Game.State == Game.STATE.OPTIONS) {
			//back button
			if(point.getX() >= 10 && point.getX() <= 210) {
				if (point.getY() >= 30 && point.getY() <= 110) {
				}
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.State == Game.STATE.MENU) {
			//play button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 150 && my <= 230) {
					playH = (false);
				}
			}
			
			//options button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 250 && my <= 330) {
					optionsH = (false);
				}
			}
			//help button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 350 && my <= 430) {
					helpH = (false);
				}
			}
			//exit button
			if(mx >= Game.WIDTH/2-80 && mx <= Game.WIDTH/2 + 120) {
				if (my >= 450 && my <= 530) {
					exitH = (false);
				}
			}
		} else if (Game.State == Game.STATE.HELP) {
			//back button
			if(mx >= 10 && mx <= 210) {
				if (my >= 30 && my <= 110) {
				}
			}
		} else if (Game.State == Game.STATE.OPTIONS) {
			//back button
			if(mx >= 10 && mx <= 210) {
				if (my >= 30 && my <= 110) {
				}
			}
		}
	}
}