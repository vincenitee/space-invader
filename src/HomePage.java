import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/*
 * Created by JFormDesigner on Mon Dec 12 16:27:09 SGT 2022
 */



/**
 * @author Vincent
 */
public class HomePage extends JFrame {
	public HomePage() {
		initComponents();
	}
	
	private void startbtnActionPerformed(ActionEvent e) {
		
		clickSound();
		homepanel.setVisible(false);
		startbtn.setVisible(false);
		initPanel();
		timerStart();
		
	}
	
	private void clickSound() {
		sfxFile = new File("sfx/blaster-2-81267.wav");
		try {
			audioStream2 = AudioSystem.getAudioInputStream(sfxFile);
			clip2 = AudioSystem.getClip();
			clip2.open(audioStream2);
			clip2.start();
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	private void timerStart() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int prog = loadProgBar.getValue();
				prog++;
				loadProgBar.setValue(prog);
				if(prog == 100) {
					timer.cancel();
					dispose();
					new GameFrame();
				}
			}
		}, 100, 10);
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		homepanel = new JPanel();
		spaceinvaderlogo = new JLabel();
		pressstart = new JLabel();
		startbtn = new JButton();
		backgroundcolor = new JLabel();

		//======== this ========
		setTitle("Space Invader");
		setIconImage(new ImageIcon("images/icons8-space-invaders-24.png").getImage());
		setBackground(Color.black);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
		contentPane.setLayout(null);

		//======== homepanel ========
		{
			homepanel.setOpaque(false);
			homepanel.setLayout(new GridLayout(2, 0, 0, 10));

			//---- spaceinvaderlogo ----
			spaceinvaderlogo.setIcon(new ImageIcon("images\\space-invader-logo.jpg"));
			spaceinvaderlogo.setHorizontalAlignment(SwingConstants.CENTER);
			homepanel.add(spaceinvaderlogo);

			//---- pressstart ----
			pressstart.setIcon(new ImageIcon("images\\press start.gif"));
			pressstart.setHorizontalAlignment(SwingConstants.CENTER);
			homepanel.add(pressstart);
		}
		contentPane.add(homepanel);
		homepanel.setBounds(20, 20, 435, 440);

		//---- startbtn ----
		startbtn.setText("START");
		startbtn.setBackground(Color.black);
		startbtn.setForeground(Color.white);
		startbtn.setFocusable(false);
		startbtn.setFont(new Font("Segoe UI Black", Font.PLAIN, 26));
		startbtn.addActionListener(e -> startbtnActionPerformed(e));
		contentPane.add(startbtn);
		startbtn.setBounds(134, 485, 210, 45);

		//---- backgroundcolor ----
		backgroundcolor.setBackground(Color.black);
		backgroundcolor.setOpaque(true);
		contentPane.add(backgroundcolor);
		backgroundcolor.setBounds(0, 0, 475, 565);

		contentPane.setPreferredSize(new Dimension(475, 565));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	private void initPanel() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		loadProgBar = new JProgressBar();
		label1 = new JLabel();
		label2 = new JLabel();
		backgroundcolor2 = new JLabel();

		//======== this ========
		setLayout(null);
		setVisible(true);

		//---- loadProgBar ----
		loadProgBar.setForeground(Color.black);
		loadProgBar.setBackground(Color.gray);
		loadProgBar.setValue(0);
		loadProgBar.setStringPainted(true);
		loadProgBar.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		add(loadProgBar);
		loadProgBar.setBounds(24, 500, 430, 29);

		//---- label1 ----
		label1.setVerticalAlignment(SwingConstants.BOTTOM);
		label1.setText("LOADING....");
		label1.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		label1.setForeground(Color.white);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		add(label1);
		label1.setBounds(121, 465, 235, 25);

		//---- label2 ----
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setIcon(new ImageIcon("images\\spaceinvaders-loadingscreen.gif"));
		add(label2);
		label2.setBounds(new Rectangle(new Point(127, 175), label2.getPreferredSize()));

		//---- backgroundcolor2 ----
		backgroundcolor.setBackground(Color.black);
		backgroundcolor.setOpaque(true);
		add(backgroundcolor);
		backgroundcolor2.setBounds(0, 0, 475, 565);

		setPreferredSize(new Dimension(475, 565));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
	
	public static void main(String args[]) {
		new HomePage().setVisible(true);
	}
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel homepanel;
	private JLabel spaceinvaderlogo;
	private JLabel pressstart;
	private JButton startbtn;
	private JLabel backgroundcolor;
	private Container contentPane = getContentPane();
	private JProgressBar loadProgBar;
	private JLabel label1;
	private JLabel label2;
	private JLabel backgroundcolor2;
	private Timer timer;
	private File sfxFile;
	private AudioInputStream audioStream2;
	private Clip clip2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
