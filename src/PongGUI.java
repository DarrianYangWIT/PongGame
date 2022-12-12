import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JPanel;

public class PongGUI extends JFrame implements ActionListener {

	JLabel label;
	JPanel panel; 
	JFrame frame; 
	
	public PongGUI() {
		
		JFrame frame = new JFrame(); 
		
		JButton button = new JButton("Start Game");
		button.addActionListener(this);
		
		
		JLabel label = new JLabel("Pong");
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new GridLayout(0,1)); 
		panel.add(button);
		panel.add(label);
		
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pong Game");
		frame.pack();
		frame.setVisible(true);
		
		
	}
	

	
}
