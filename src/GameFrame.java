import java.awt.*;
import javax.swing.*; 

/**
 * GameFrame is a class that acts as the frame
 * around a painting 
 * 
 * @author Darrian Yang 
 *
 */
public class GameFrame extends JFrame{

	GamePanel panel;
	
	
	GameFrame(){
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Pong Game");; 
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		}
			
}
