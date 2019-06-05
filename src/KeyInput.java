import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	Game game;
	
	public KeyInput(Game game) {
		this.game = game;//makes keyboard input available no matter where character is
	}
	
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}