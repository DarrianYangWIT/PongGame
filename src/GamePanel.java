import java.awt.*;
import java.awt.event.*; 
import java.util.*;
import javax.swing.*; 

/**
 * GamePanel creates everything for the 
 * interface of the game
 * 
 * @author Darrian Yang
 *
 */
public class GamePanel extends JPanel implements Runnable{

	static final int GAME_WIDTH = 1000; 
	//game height is dimensioned off real ping-pong table
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20; 
	static final int PADDLE_WIDTH = 25; 
	static final int PADDLE_HEIGHT = 100; 
	Thread gameThread; 
	Image image; 
	Graphics graphics; 
	Random random; 
	Paddles paddle1;
	Paddles paddle2; 
	Ball ball; 
	Score score; 
	
	
	GamePanel(){
		
		newPaddle();
		newBall();
		score = new Score (GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
		
		
	}
	
	public void newBall( ) { 
		
		random = new Random();
		ball = new Ball((GAME_WIDTH / 2)-(BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
		
	}
	
	public void newPaddle() {
		
		paddle1 = new Paddles(0,(GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1); 
		paddle2 = new Paddles(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2); 
		
		
		
	}
	
	public void paint(Graphics g) {
		
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
		
	}
	
	public void draw(Graphics g) {
		
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		
	}
	
	public void move() {
		
		paddle1.move();
		paddle2.move();
		ball.move();
		
	}
	
	public void checkCollision() {
		
		//bounce ball off top & bottom window edges
		if(ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}

		//bounces ball off paddles
		
		// for paddle 1
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty, will speed up ball after collision with paddle
			if(ball.yVelocity > 0)
				ball.yVelocity++; //optional for more difficulty, will increase upwards velocity 
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		// for paddle 2
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty, will speed up ball after collision with paddle
			if(ball.yVelocity > 0)
				ball.yVelocity++; //optional for more difficulty, will increase upwards velocity 
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		//stops paddles at window edges 
		
		//for paddle 1
		if(paddle1.y <= 0)
			paddle1.y = 0; 
		if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		//for paddle 2
		if(paddle2.y <= 0)
			paddle2.y = 0; 
		if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		
		//give player1 point and creates new paddles and ball
		if(ball.x <= 0) {
			score.player2++;
			newPaddle();
			newBall();
		}
		
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddle();
			newBall();
		}
	}
	
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; 
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; 
		
		while (true) {
			
			long now = System.nanoTime(); 
			delta += (now - lastTime)/ns; 
			lastTime = now; 
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--; 
				
				
				
			}
			
		}
	}
	
	/**
	 * inner class AL (action listener)
	 * @author kidxk
	 *
	 */
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
			
		}
        public void keyReleased(KeyEvent e) {
			
        	paddle1.keyReleased(e);
        	paddle2.keyReleased(e);
        	
		}
	}
}

