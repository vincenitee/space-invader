import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class GameEntity {

	protected int x;
	protected int y;
	protected int height;
	protected int width;
	private Image image;
	protected boolean alive;
	private int hitpoints;
	
	GameEntity(int x, int y){
		this.x = x;
		this.y = y;
		alive = true;
	}
	
	protected void loadImage(String path) {
		ImageIcon iconImage = new ImageIcon(path);
		image = iconImage.getImage();
	}
	
	protected void setDimensions() {
		height = image.getHeight(null);
		width = image.getWidth(null);
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHitPoints() {
		return hitpoints;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setHitPoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}
	
	public void minusHitPoints() {
		hitpoints--;
	}
	
	public void setStatus(boolean alive) {
		this.alive = alive;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
