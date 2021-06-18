import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.nio.IntBuffer;
import java.text.AttributedCharacterIterator;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



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
        		
        		int sx = e.getX() / blockSize;
        		int sy = e.getY() / blockSize;
        		
        		for(int x = -20; x < 20; x++) {
        			for(int y = -20; y < 20; y++) {
        				if(Math.sqrt(x * x + y * y) <= 20) {
	        				if(e.getButton() == MouseEvent.BUTTON1) {
		        				grid.setBlock(BlockId.values()[butId], sx + x, sy + y);   
	        				}
	        				if(e.getButton() == MouseEvent.BUTTON3) {
	        					grid.setBlock(BlockId.None, sx + x, sy + y); 
	        				}
        				}
        			}
        		}
        		  		
        	}
			@Override public void mousePressed(MouseEvent e) {}
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
        		
        		update();
        		
        		frame.repaint();
        	}
        	lastTime = currentTime;
        }
    }
    
    public void update() {
    	
    	for(int y = sizey - 1; y >= 0; y--) {
    		for(int x = 0; x < sizex; x++) {
        		Block block = grid.get(x, y);
        		if(block.lastUpdated >= currentFrame) {
            		continue;
            	}
            		
            	block.lifeTime--;

    			if (block.lifeTime <= 0){
    				grid.setBlock(BlockId.None, x, y);
    				continue;
    			}
            		
            	block.lastUpdated = currentFrame;
        		block.update(x, y, grid);
        	}
    	}
    }
    
 
    
    public class Window extends JLabel{
    	
    	@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		
    		for(int y = 0; y < sizey; y++) {
    			for(int x = 0; x < sizex; x++) {
            		g.setColor(grid.get(x, y).color);
                	g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }
            }   
        }
    	
    }
    
}


