package Game;

import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.ZonedDateTime;
import java.util.Date;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


import Blocks.*;


public class Game  {
	
	static Grid grid;
	static Date date;
	
	static int sizex;
	static int sizey;
	static int blockSize;
	
	static int butId = 1;
	
	static long currentFrame = 0;
	
	static long lastTime = 0;
	static long elapsedTime = 0;
	static long fps = 1000 / 1000; 
	
	int currMouseButton;
	boolean isClicking;
	
	public void initGrid(int sx, int sy, int bs) {
		sizex = sx;
		sizey = sy;
		blockSize = bs;
		grid = new Grid(sx, sy, bs);
	}
	
	public static long getMillis() {
		return ZonedDateTime.now().toInstant().toEpochMilli();
	}
	
	
	public static int getRandomInt(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
	}
	
	public static boolean roll(int chance) {
		int roll = getRandomInt(0, chance);
		if(roll == 0)
			return true;
		
		return false;
	}
	
	public void run() throws InterruptedException {
    	
    	initGrid(600, 400, 2);
    	date = new Date();
    	JFrame frame = new JFrame("Particles");
        
    	Window window = new Window();
        frame.add(window);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(sizex * blockSize, sizey * blockSize + 100);
        frame.setResizable(false);
        lastTime = getMillis();
        
        for(int i = 1; i < BlockId.values().length - 1; i++) {
	        JButton but = new JButton(BlockId.values()[i].name());
	        but.setVisible(true);
	        but.setLayout(null);
	        but.setBounds(2 + 102 * (i - 1), sizey * blockSize + 2, 100, 50);
	        but.putClientProperty("id", i);
	        window.add(but);
	        but.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();
					if (obj instanceof JButton) {
					    JButton cb = (JButton)obj;
					    butId = (int)cb.getClientProperty("id");
					}
				}
			});
        }
        
        frame.addMouseListener(new MouseListener() {
        	@Override public void mouseReleased(MouseEvent e) {
        		isClicking = false;
        		
        		  		
        	}
			@Override public void mousePressed(MouseEvent e) {
				isClicking = true;
				currMouseButton = e.getButton();
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseClicked(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
        
        
        
        while(true) {
        	long currentTime = getMillis();
        	elapsedTime += currentTime - lastTime;
        	
        	
        	if(elapsedTime > fps) {
        		elapsedTime = 0;
        		currentFrame++;
        		
        		update(frame);
        		
        		frame.repaint();
        	}
        	lastTime = currentTime;
        }
    }
    
    public void update(JFrame frame) {
    	
    	if(isClicking) {
    		int sx, sy;
    		
    		try {
    			sx = frame.getMousePosition().x / blockSize;
        		sy = frame.getMousePosition().y / blockSize;
    		}
    		catch(NullPointerException e) {
    			sx = -10000;
    			sy = -10000;
    		}
        	
    		
    		for(int x = -20; x < 20; x++) {
    			for(int y = -20; y < 20; y++) {
    				if(Math.sqrt(x * x + y * y) <= 20) {
        				if(currMouseButton == MouseEvent.BUTTON1) {
	        				grid.setBlock(BlockId.values()[butId], sx + x, sy + y);   
        				}
        				if(currMouseButton == MouseEvent.BUTTON3) {
        					grid.setBlock(BlockId.None, sx + x, sy + y); 
        				}
    				}
    			}
    		}
        }
    	
    	for(int y = sizey - 1; y >= 0; y--) {
    		for(int x = 0; x < sizex; x++) {
        		Block block = grid.get(x, y);
        		
        		if(block.isId(BlockId.None))
        			continue;
        		
        		if(block.getLastUpdated() >= currentFrame) {
            		continue;
            	}
            		
            	block.setLifeTime(block.getLifeTime() - 1);

    			if (block.getLifeTime() <= 0){
    				grid.setBlock(BlockId.None, x, y);
    				continue;
    			}
            		
            	block.setLastUpdated(currentFrame);
        		block.update(x, y, grid);
        	}
    	}
    }
    
 
    
    public class Window extends JLabel{
    	
		private static final long serialVersionUID = -3280536400661865030L;

		@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		
    		for(int y = 0; y < sizey; y++) {
    			for(int x = 0; x < sizex; x++) {
            		g.setColor(grid.get(x, y).getColor());
                	g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }
            }   
        }
    	
    }
    
}


