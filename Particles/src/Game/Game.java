package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Blocks.*;


public class Game{
	
	static Grid grid;
	static Date date;
	
	static int sizex;
	static int sizey;
	static int blockSize;
	static int viewOffsetX = 0;
	static int viewOffsetY = 0;
	static boolean keyW = false;
	static boolean keyS = false;
	static boolean keyA = false;
	static boolean keyD = false;
	
	static int butId = 1;
	
	static int placementRadius = 20;
	
	static long currentFrame = 0;
	
	static long lastTime = 0;
	static long elapsedTime = 0;
	static long fps = 1; 
	public static final int coreCount = Runtime.getRuntime().availableProcessors();
	
	int currMouseButton;
	boolean isClicking;
	
	WorldCanvas worldCanvas;
	
	public static long getCurrentFrame() {
		return currentFrame;
	}
	
	public void initGrid(int sx, int sy, int bs) {
		sizex = sx;
		sizey = sy;
		blockSize = bs;
		grid = new Grid(sx, sy);
	}
	
	public static long getMillis() {
		return ZonedDateTime.now().toInstant().toEpochMilli();
	}
	
	
	public static int getRandomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}
	
	/**
	 * Gibt mit der angegeben Chance true zurück.
	 * Parameter = 50, Chanche von 1:50 true zu returnen.
	 * 
	 * @param chance
	 * @return boolean
	 */
	public static boolean roll(int chance) {
		int roll = getRandomInt(0, chance);
		if(roll == 0)
			return true;
		
		return false;
	}
	
	public void run(String[] args) throws InterruptedException {
    	
		
    	initGrid(1200, 600, 2);
    	date = new Date();
    	
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
    	
    	JFrame frame = new JFrame("Particles");
        
    	worldCanvas = new WorldCanvas();
        frame.add(worldCanvas);
        
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
	        worldCanvas.add(but);
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
        
        JSlider slider = new JSlider(0, 50, 20);
        slider.setPreferredSize(new Dimension(100, 50));
        slider.setBounds(sizex * blockSize - 150, sizey * blockSize + 2, 100, 50);
        slider.setVisible(true);
        slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				placementRadius = slider.getValue();
				
			}
		});
        worldCanvas.add(slider);
        
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
        
        frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W: keyW = false; break;
				case KeyEvent.VK_S: keyS = false; break;
				case KeyEvent.VK_A: keyA = false; break;
				case KeyEvent.VK_D: keyD = false; break;
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W: keyW = true; break;
				case KeyEvent.VK_S: keyS = true; break;
				case KeyEvent.VK_A: keyA = true; break;
				case KeyEvent.VK_D: keyD = true; break;
				}
			}
		});
        
        frame.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int deltaScroll = e.getWheelRotation();
				
				
				
				blockSize += deltaScroll;
				if(blockSize < 1)blockSize = 1;
				if(blockSize > 9)blockSize = 9;
			}
		});
        
        
        
        int fpsCounter = 0;
        long lastSecond = 0;
        
        while(true) {
        	long currentTime = getMillis();
        	elapsedTime += currentTime - lastTime;
        	
        	long currSecond = currentTime / 1000;
        	if(currSecond != lastSecond) {
        		lastSecond = currSecond;
        		frame.setTitle("FPS: " + fpsCounter + " Cores: " + coreCount);
        		fpsCounter = 0;
        	}
        	
        	
        	if(elapsedTime > fps) {
        		elapsedTime = 0;
        		currentFrame++;
        		
        		update(frame);
        		
        		frame.repaint();
        		fpsCounter++;
        	}
        	
        	lastTime = currentTime;
        	
        }
    }
	
    public void update(JFrame frame) throws InterruptedException {
    	
    	frame.requestFocus();
    	
    	if(isClicking) {
    		int sx, sy;
    		
    		try {
    			sx = (int)((float)worldCanvas.getMousePosition().x / (float)blockSize / (float)2 + (float)viewOffsetX);
        		sy = (int)((float)worldCanvas.getMousePosition().y / (float)blockSize / (float)2 + (float)viewOffsetY);
    		}
    		catch(NullPointerException e) {
    			sx = -10000;
    			sy = -10000;
    		}
        	
    		
    		for(int x = -placementRadius; x <= placementRadius; x++) {
    			for(int y = -placementRadius; y <= placementRadius; y++) {
    				if(Math.sqrt(x * x + y * y) <= placementRadius) {
        				if(currMouseButton == MouseEvent.BUTTON1) {
	        				grid.generateNewBlock(BlockId.values()[butId], sx + x, sy + y);   
        				}
        				if(currMouseButton == MouseEvent.BUTTON3) {
        					grid.generateNewBlock(BlockId.None, sx + x, sy + y); 
        				}
    				}
    			}
    		}
        }
    	
    	if(keyW)viewOffsetY--;
    	if(keyS)viewOffsetY++;
    	if(keyA)viewOffsetX--;
    	if(keyD)viewOffsetX++;
    	
    	int threadAmount = coreCount + getRandomInt(-1, 10);
    	int partSize = sizex / threadAmount;
    	
    	ExecutorService executor = Executors.newFixedThreadPool(threadAmount);
    	
    	for(int i = 0; i < threadAmount; i++) {
    		executor.submit(new UpdateThread(i * partSize, (i + 1) * partSize));
    	}
    	
    	executor.shutdown();
    	executor.awaitTermination(1, TimeUnit.DAYS);
    	
    	
    }
    
    public class UpdateThread implements Runnable{

    	int start;
    	int end;
    	
    	public UpdateThread(int start, int end) {
    		this.start = start;
    		this.end = end;
    	}
    	
		@Override
		public void run() {
			switch(getRandomInt(0, 3)) {
			case 0:
				for(int x = start; x < end; x++) {
					for(int y = sizey - 1; y >= 0; y--) {
						updateBlock(x, y);
		        	}
		    	}
				break;
			case 1:
				for(int y = sizey - 1; y >= 0; y--) {
					for(int x = start; x < end; x++) {
						updateBlock(x, y);
		        	}
		    	}
				break;
			case 2:
				for(int x = start; x < end; x++) {
					for(int y = 0; y < sizey; y++) {
						updateBlock(x, y);
		        	}
		    	}
				break;
			case 3:
				for(int y = sizey - 1; y >= 0; y--) {
					for(int x = end -1; x >= 0; x--) {
						updateBlock(x, y);
		        	}
		    	}
				break;
			}
		}
    }
    
    public void updateBlock(int x, int y) {
    	Block block = grid.get(x, y);
		
		if(block.isId(BlockId.None))
			return;
		
		if(block.getLastUpdated() >= currentFrame) {
			return;
    	}
    		
    	block.setLifeTime(block.getLifeTime() - 1);

		if (block.getLifeTime() <= 0){
			grid.generateNewBlock(BlockId.None, x, y);
			return;
		}
		
    	block.setLastUpdated(currentFrame);
		block.update(x, y, grid);
    }
    
    public class WorldCanvas extends JLabel{
    	
		private static final long serialVersionUID = -3280536400661865030L;
		
		
		@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		
    		int renderX = sizex / blockSize;
    		int renderY = sizey / blockSize;
    		
    		int[] pixelArray = new int[renderX * renderY];
    		BufferedImage img = new BufferedImage( renderX, renderY, BufferedImage.TYPE_INT_RGB );
   
    		for(int y = 0; y < renderY; y++) {
    			for(int x = 0; x < renderX; x++) {
    				pixelArray[y * renderX + x] = grid.get(x + viewOffsetX, y + viewOffsetY).getColor().getRGB();
                }
            }  
    		
    		
    		final int[] a = ( (DataBufferInt)img.getRaster().getDataBuffer() ).getData();
    		System.arraycopy(pixelArray, 0, a, 0, pixelArray.length);

    		
    		g.drawImage(img, 0, 0, renderX * blockSize * 2, renderY * blockSize * 2, null);
        }
    	
    }
    
    public class DrawThread implements Runnable{

    	int drawstart;
    	int drawend;
    	int[] pixelArray;
    	
    	public DrawThread(int drawstart, int drawend, int[] pixelArray) {
    		this.drawstart = drawstart;
    		this.drawend = drawend;
    		this.pixelArray = pixelArray;
    	}
    	
		@Override
		public void run() {
			for(int y = drawstart; y < drawend; y++) {
    			for(int x = 0; x < sizex; x++) {
    				pixelArray[y * sizex + x] = grid.get(x, y).getColor().getRGB();
                }
            }  
		}
    	
    }
    
}


