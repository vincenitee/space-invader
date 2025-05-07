
public class EnemyRockets extends GameEntity{

	private int speed;
	EnemyRockets(int x, int y) {
		super(x, y);
		
		initErockets();
	}
	
	public void initErockets() {
		speed = 3;
		loadImage("images/enemyBullets.png");
		setBounds(x, y, 16, 25);
	}
	
	public void move() {
		y += speed;
		
		if(y >= 600) {
			setStatus(false);
		}
	}

}
