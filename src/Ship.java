import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.io.*;
import javax.sound.sampled.*;

public class Ship extends GameEntity {

	private int ship_speed;
	private GamePanel panel;
	private KeyControls keyControls;
	private ArrayList<ShipRockets> srockets;
	private File sfxFile;
	private AudioInputStream audioStream;
	private Clip clip;
	
	Ship(int x, int y, GamePanel panel, KeyControls keyControls) {
		super(x, y);
		
		this.panel = panel;
		this.keyControls = keyControls;
		
		initShip();
	}
	 
	public void initShip() {
		srockets = new ArrayList<>();
		ship_speed = 3;
		
		loadImage("images/ship-trek.png");
		setDimensions();
		setHitPoints(5);
	}
	
	public void soundEffects() {
		sfxFile = new File("sfx/blaster-2-81267.wav");
		try {
			audioStream = AudioSystem.getAudioInputStream(sfxFile);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void shoot() {
		srockets.add(new ShipRockets(x+19, y-10));
		
	}
	
	public ArrayList<ShipRockets> getRockets() {
		return srockets;
	}
	
	public void mouseReleased(MouseEvent e) {
		soundEffects();
		shoot();
	}
	
	public void gCompleteMove() {
		y -= 10;
	}
	
	public void move() {
			
		//==== checks the border of the screen ====
		{
			if (x < 1) {
				x = 1;
			}

			if (y < 1) {
				y = 1;
			}
		
			if(x >= panel.getPanelWidth() - width) {
				x = 399;
			}
        
			if(y >= panel.getPanelHeight() - height) {
				y = 542;
			}
		}
		
		/*==== controls ====
		 * the direction of the ship 
		 * depends on what key is pressed
		 */
		
		{
			if(keyControls.upClicked) {
				y -= ship_speed;
			}
		
			if(keyControls.downClicked) {
				y += ship_speed;
			}
		
			if(keyControls.leftClicked) {
				x -= ship_speed;
			}
		
			if(keyControls.rightClicked) {
				x += ship_speed;
			}
		}
		
	}
	

}
