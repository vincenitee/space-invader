import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class Enemy extends GameEntity implements ActionListener{

	private Timer timer;
	private final int DELAY = 5000;
	private double xVelocity;
	private int yVelocity;
	private ArrayList<EnemyRockets> erockets;
	
	Enemy(int x, int y) {
		super(x, y);		
		initEnemy();
	}
	
	public void initEnemy() {
		
		erockets = new ArrayList<>();
		timer = new Timer(DELAY, this);
		xVelocity = 1.5;
		yVelocity = 1;
		loadImage("images/icons8-ufo-30.png");
		setDimensions();
		setHitPoints(5);
		
	}
	
	public ArrayList<EnemyRockets> getRockets(){
		return erockets;
	}
	
	public void shoot() {
		erockets.add(new EnemyRockets(x + 5, y + 5));
	}
	
	public void move() {
		
		
		//==== Enemy Ship's Weapon ====
		{
			if(y >= 0) {
				timer.start();
			}
		
			if(y > 300) {
				timer.stop();
			}
		}

		//==== Enemy Ship's Movement ====
		{
			if(x >= (456 - width) | x < 0) {
				xVelocity *= -1;
				if(y <= 50) {
					y += yVelocity;
				}
				else if(y <= 60) {
					y += yVelocity;
				}
			}
				
			if(y >= (250 - height) | y < 0)  {
				yVelocity *= -1;
			}
				x += xVelocity;
				y += yVelocity;
		}


		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		shoot();
	}

}
