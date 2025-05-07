
public class ShipRockets extends GameEntity {

	private int rocketspd;
	public ShipRockets(int x, int y) {
		super(x, y);
		initRockets();
	}
	
	public void initRockets() {
		rocketspd = 4;
		loadImage("images/bullets.png");
		setBounds(x, y, 16, 25);
	}
	
	public void move() {
		y -= rocketspd;
		
		if(y < 0) {
			setStatus(false);
		}
	}

}
