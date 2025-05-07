import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

	GameFrame(){
		initFrame();
	}
	
	public void initFrame() {
		setIconImage(new ImageIcon("images/icons8-space-invaders-24.png").getImage());
		add(new GamePanel());
		setTitle("Space Invader");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		
		setLocationRelativeTo(null);
	}
}
