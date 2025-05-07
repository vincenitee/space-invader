import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyControls extends KeyAdapter {
	
	public boolean upClicked;
	public boolean downClicked;
	public boolean leftClicked;
	public boolean rightClicked;
	public boolean weapon;
	private int key;
	
	@Override
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				upClicked = true;
				break;
				
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				downClicked = true;
				break;
				
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				leftClicked = true;
				break;
				
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightClicked = true;
				break;
				
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();
		
		switch(key){
				
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				upClicked = false;
				break;
			
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				downClicked = false;
				break;
			
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				leftClicked = false;
				break;
			
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightClicked = false;
				break;
		}
		
	}
	
	
}
